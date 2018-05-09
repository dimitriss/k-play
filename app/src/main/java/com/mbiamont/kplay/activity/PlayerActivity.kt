package com.mbiamont.kplay.activity

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mbiamont.kplay.R
import com.mbiamont.kplay.di.GlideApp
import com.mbiamont.kplay.presenter.PlayerPresenter
import com.mbiamont.kplay.view.PlayerView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Activity to play playlist
 */
class PlayerActivity : FullscreenActivity(), PlayerView {

    companion object {
        /**
         * Extra key to exchange the playlist which must be played
         */
        const val EXTRA_PLAYLIST = "com.mbiamont.kplay.extras.PLAYLIST"

        /**
         * Extra key to exchange the position of the track to be played in the given playlist
         */
        const val EXTRA_POSITION = "com.mbiamont.kplay.extras.POSITION"
    }

    override var activity: Activity = this

    @Inject
    lateinit var playerPresenter: PlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        AndroidInjection.inject(this)
        playerPresenter.playerView = this

        initView()
        playerPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerPresenter.onDestroy()
    }

    /**
     * Setup the view
     */
    private fun initView() {
        sbProgress.setOnTouchListener({ _, _ -> true })
        btPlay.setOnClickListener({ playerPresenter.togglePlay() })
        btNext.setOnClickListener({ playerPresenter.next() })
        btPrevious.setOnClickListener({ playerPresenter.previous() })
        btReduce.setOnClickListener({ playerPresenter.back() })
    }

    override fun showTitle(title: String) {
        lblTitle.text = title
    }

    override fun showArtist(artist: String) {
        lblArtist.text = artist
    }

    override fun showCover(url: String) {
        GlideApp
                .with(this)
                .load(url)
                .placeholder(R.drawable.bg_placeholder_cover)
                .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(10)))
                .into(icCover)
    }

    override fun showCurrentTiming(currentTiming: String) {
        lblCurrentTiming.text = currentTiming
    }

    override fun showProgress(progress: Int) {
        sbProgress.progress = progress
    }

    override fun updatePlayPauseState(isPlaying: Boolean) {
        btPlay.setImageResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
    }

    override fun showErrorMessage(message: Int) {
        Snackbar.make(icCover, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun terminate() {
        finish()
    }
}