package com.mbiamont.kplay.data.rest

import com.mbiamont.kplay.data.model.TrackList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Melvin Biamont
 *
 * Retrofit2 service to access Deezer API
 */
interface TrackService {
    companion object {
        const val BASE_URL = "https://api.deezer.com"
    }

    @GET("/chart/0/tracks")
    fun getTopTracks(@Query("index") index: Int): Single<TrackList>

    @GET("/search")
    fun searchTrack(@Query("q") query: String, @Query("index") index: Int): Single<TrackList>
}