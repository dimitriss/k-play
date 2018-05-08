package com.mbiamont.kplay.presenter

import com.mbiamont.kplay.R
import com.mbiamont.kplay.domain.usecase.LoginUsecase
import com.mbiamont.kplay.domain.usecase.TrackUsecase
import com.mbiamont.kplay.view.TracksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class TracksPresenter @Inject constructor(private val loginUsecase: LoginUsecase, private val trackUsecase: TrackUsecase) {

    lateinit var tracksView: TracksView

    private var currentIndex = 0
    private var lastSearch = ""

    var isRequestPending = false

    fun onCreate() {
        currentIndex = 0
        retrieveTopTracks()
    }

    fun onStart() {
        if (!loginUsecase.isLogged(tracksView.context)) {
            tracksView.navigateToLogin()
        }
    }

    fun loadMore() {
        if (isRequestPending) {
            return
        }
        if (lastSearch.isEmpty()) {
            retrieveTopTracks()
        } else {
            search(lastSearch)
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            lastSearch = ""
            currentIndex = 0
            retrieveTopTracks()
            return
        } else if (lastSearch != query) {
            currentIndex = 0
            tracksView.setSubTitle(
                    String.format(
                            Locale.getDefault(),
                            tracksView.context.getString(R.string.subtitle_search),
                            query))
        }
        lastSearch = query

        trackUsecase.searchTrack(query, currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isRequestPending = true }
                .subscribe({
                               isRequestPending = false
                               if (currentIndex == 0) {
                                   tracksView.resetTracks(it)
                               } else {
                                   tracksView.addNewTracks(it)
                               }
                               currentIndex += it.total
                           }, {
                               isRequestPending = false
                               tracksView.showErrorMessage(R.string.error_unknown)
                           })
    }

    fun retrieveTopTracks() {
        tracksView.setSubTitle(tracksView.context.getString(R.string.subtitle_top))

        trackUsecase.getTopTrack(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isRequestPending = true }
                .subscribe({
                               isRequestPending = false
                               if (currentIndex == 0) {
                                   tracksView.resetTracks(it)
                               } else {
                                   tracksView.addNewTracks(it)
                               }
                               currentIndex += it.total
                           }, {
                               isRequestPending = false
                               tracksView.showErrorMessage(R.string.error_unknown)
                           })
    }
}