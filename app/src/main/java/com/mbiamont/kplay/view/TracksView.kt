package com.mbiamont.kplay.view

import android.content.Context
import com.mbiamont.kplay.data.model.Track
import com.mbiamont.kplay.data.model.TrackList

interface TracksView {

    fun addNewTracks(tracks: TrackList)

    fun resetTracks(tracks: TrackList)

    fun showErrorMessage(message: Int)

    fun navigateToLogin()

    fun navigateToPlayer(trackList: List<Track>, selectedPosition: Int)

    fun setSubTitle(subTitle: String)

    var context: Context
}