package com.mbiamont.kplay.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.irozon.sneaker.Sneaker
import com.mbiamont.kplay.R
import com.mbiamont.kplay.activity.PlayerActivity.Companion.EXTRA_PLAYLIST
import com.mbiamont.kplay.activity.PlayerActivity.Companion.EXTRA_POSITION
import com.mbiamont.kplay.adapter.OnTrackClickedListener
import com.mbiamont.kplay.adapter.TracksAdapter
import com.mbiamont.kplay.data.model.Track
import com.mbiamont.kplay.data.model.TrackList
import com.mbiamont.kplay.listener.EndlessRecyclerViewScrollListener
import com.mbiamont.kplay.presenter.TracksPresenter
import com.mbiamont.kplay.view.TracksView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_tracks.*
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Activity to list the tracks by TOP100 or search
 */
class TracksActivity : AppCompatActivity(), TracksView, OnTrackClickedListener {

    override var context: Context = this

    @Inject
    lateinit var tracksPresenter: TracksPresenter

    private lateinit var tracksAdapter: TracksAdapter

    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracks)
        AndroidInjection.inject(this)
        tracksPresenter.tracksView = this
        tracksAdapter = TracksAdapter(this, this)

        initView()

        tracksPresenter.onCreate()
    }

    override fun onStart() {
        super.onStart()
        tracksPresenter.onStart()
    }

    /**
     * Setup view
     */
    private fun initView() {
        btSearch.setOnClickListener({ tracksPresenter.search(txtSearch.text.toString()) })

        btListen.setOnClickListener({
                                        Snackbar.make(listTracks, R.string.coming_soon,
                                                      Snackbar.LENGTH_SHORT).show()
                                    })

        //listener when the user press the search key on the keypad
        txtSearch.setOnEditorActionListener(
                TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                                .hideSoftInputFromWindow(txtSearch.windowToken, 0)
                        tracksPresenter.search(txtSearch.text.toString())
                        return@OnEditorActionListener true
                    }
                    return@OnEditorActionListener false
                })

        val layoutManager = LinearLayoutManager(this)

        endlessRecyclerOnScrollListener = object : EndlessRecyclerViewScrollListener(
                layoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                tracksPresenter.loadMore()
            }
        }

        listTracks.setHasFixedSize(true)
        listTracks.layoutManager = layoutManager
        listTracks.adapter = tracksAdapter


        listTracks.addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    override fun onTrackClicked(trackList: List<Track>, position: Int) {
        navigateToPlayer(trackList, position)
    }

    override fun showErrorMessage(message: Int) {
        Sneaker.with(this)
                .setTitle(getString(R.string.error_unknown_title))
                .setMessage(getString(message))
                .sneakError()
    }

    override fun resetTracks(tracks: TrackList) {
        tracksAdapter.resetTracks(tracks.data)
        endlessRecyclerOnScrollListener.resetState()
    }

    override fun addNewTracks(tracks: TrackList) {
        tracksAdapter.addNewTracks(tracks.data)
        endlessRecyclerOnScrollListener.resetState()
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun navigateToPlayer(trackList: List<Track>, selectedPosition: Int) {
        startActivity(Intent(this, PlayerActivity::class.java)
                              .putParcelableArrayListExtra(EXTRA_PLAYLIST, tracksAdapter.trackList)
                              .putExtra(EXTRA_POSITION, selectedPosition))
    }

    override fun setSubTitle(subTitle: String) {
        tracksAdapter.subtitle = subTitle
    }
}