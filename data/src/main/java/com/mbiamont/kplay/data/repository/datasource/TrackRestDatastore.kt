package com.mbiamont.kplay.data.repository.datasource

import com.mbiamont.kplay.data.model.TrackList
import com.mbiamont.kplay.data.rest.TrackService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Datastore implementation to access Tracks using Rest API
 */
class TrackRestDatastore @Inject constructor(private val trackService: TrackService) : TrackDatastore {

    override fun getTopTrack(index: Int): Single<TrackList> {
        return trackService.getTopTracks(index)
    }

    override fun searchTrack(query: String, index: Int): Single<TrackList> {
        return trackService.searchTrack(query, index)
    }
}