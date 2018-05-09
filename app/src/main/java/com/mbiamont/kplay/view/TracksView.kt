package com.mbiamont.kplay.view

import android.content.Context
import com.mbiamont.kplay.data.model.Track
import com.mbiamont.kplay.data.model.TrackList

/**
 * Created by Melvin Biamont
 *
 * Contract to interact with the tracks view
 */
interface TracksView {

    /**
     * Provides the context (required by Deezer)
     */
    var context: Context

    /**
     * Display new tracks to the current list
     */
    fun addNewTracks(tracks: TrackList)

    /**
     * Remove current tracks, and display the new ones
     */
    fun resetTracks(tracks: TrackList)

    /**
     * Redirect the user to the login view
     */
    fun navigateToLogin()

    /**
     * Redirect the user to the player view
     */
    fun navigateToPlayer(trackList: List<Track>, selectedPosition: Int)

    /**
     * Change the subtitle (top 100 or search value)
     */
    fun setSubTitle(subTitle: String)

    /**
     * Show an error message using its resource ID
     */
    fun showErrorMessage(message: Int)

}