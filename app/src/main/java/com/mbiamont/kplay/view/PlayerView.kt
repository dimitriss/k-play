package com.mbiamont.kplay.view

import android.app.Activity

/**
 * Created by Melvin Biamont
 *
 * Contract to interact with the player view
 */
interface PlayerView {

    /**
     * Provides the activity (required by Deezer)
     */
    var activity: Activity

    /**
     * Show the track title
     */
    fun showTitle(title: String)

    /**
     * Show the artist name
     */
    fun showArtist(artist: String)

    /**
     * Show the track cover picture
     */
    fun showCover(url: String)

    /**
     * Show the current timing
     */
    fun showCurrentTiming(currentTiming: String)

    /**
     * Show the progress
     */
    fun showProgress(progress: Int)

    /**
     * Update the play/pause button state
     */
    fun updatePlayPauseState(isPlaying: Boolean)

    /**
     * Terminate the player view (close it)
     */
    fun terminate()

    /**
     * Show an error message using its resource ID
     */
    fun showErrorMessage(message: Int)
}