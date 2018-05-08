package com.mbiamont.kplay.view

import android.app.Activity

interface PlayerView {

    fun showTitle(title: String)

    fun showArtist(artist: String)

    fun showCover(url: String)

    fun showCurrentTiming(currentTiming: String)

    fun showProgress(progress: Int)

    fun updatePlayPauseState(isPlaying: Boolean)

    fun terminate()

    fun showErrorMessage(message: Int)

    var activity: Activity
}