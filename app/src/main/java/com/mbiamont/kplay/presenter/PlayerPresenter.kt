package com.mbiamont.kplay.presenter

import com.deezer.sdk.model.PlayableEntity
import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.player.CustomTrackListPlayer
import com.deezer.sdk.player.event.PlayerState
import com.deezer.sdk.player.event.PlayerWrapperListener
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker
import com.mbiamont.kplay.activity.PlayerActivity.Companion.EXTRA_PLAYLIST
import com.mbiamont.kplay.activity.PlayerActivity.Companion.EXTRA_POSITION
import com.mbiamont.kplay.data.model.Track
import com.mbiamont.kplay.view.PlayerView
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PlayerPresenter @Inject constructor(private val deezerConnect: DeezerConnect) {

    lateinit var playerView: PlayerView
    lateinit var trackList: List<Track>
    lateinit var trackPlayer: CustomTrackListPlayer

    fun onCreate() {
        if (!playerView.activity.intent.extras.containsKey(EXTRA_PLAYLIST)) {
            throw RuntimeException("Extra $EXTRA_PLAYLIST missing")
        }

        trackList = playerView.activity.intent
                .getParcelableArrayListExtra(EXTRA_PLAYLIST)

        val position = playerView.activity.intent.getIntExtra(EXTRA_POSITION, 0)

        trackPlayer = CustomTrackListPlayer(playerView.activity.application,
                                            deezerConnect,
                                            WifiAndMobileNetworkStateChecker())

        trackPlayer.playTrackList(extractPlaylist(), position)

        trackPlayer.addOnPlayerProgressListener {
            playerView.showProgress(it.toInt())
            playerView.showCurrentTiming(formatTime(it))
        }

        trackPlayer.addPlayerListener(object : PlayerWrapperListener {

            override fun onAllTracksEnded() {
            }

            override fun onPlayTrack(track: PlayableEntity) {
                playerView.showProgress(0)
                playerView.updatePlayPauseState(true)
                if (track is com.deezer.sdk.model.Track) {
                    playerView.showTitle(track.title)
                    playerView.showArtist(track.artist.name)
                    playerView.showCover(track.album.bigImageUrl)
                }

            }

            override fun onRequestException(p0: Exception?, p1: Any?) {
                playerView.updatePlayPauseState(false)
            }

            override fun onTrackEnded(p0: PlayableEntity?) {
                playerView.updatePlayPauseState(false)
            }

        })

        trackPlayer.play()
    }

    fun onDestroy() {
        trackPlayer.stop()
        trackPlayer.release()
    }

    fun togglePlay() {
        playerView.updatePlayPauseState(!isPlaying())
        if (isPlaying()) {
            trackPlayer.pause()
        } else {
            trackPlayer.play()
        }
    }

    fun next() {
        trackPlayer.skipToNextTrack()
    }

    fun previous() {
        trackPlayer.skipToPreviousTrack()
    }

    fun back() {
        playerView.terminate()
    }

    private fun extractPlaylist(): List<String> {
        return trackList.map { it.id.toString() }
    }

    private fun formatTime(millis: Long): String {
        return SimpleDateFormat("m:ss", Locale.getDefault()).format(Date(millis))
    }

    private fun isPlaying(): Boolean {
        return when (trackPlayer.playerState) {
            PlayerState.PLAYING, PlayerState.STARTED ->
                true
            else -> {
                false
            }
        }
    }
}