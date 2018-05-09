package com.mbiamont.kplay.data.repository.datasource

import com.mbiamont.kplay.data.model.TrackList
import io.reactivex.Single

/**
 * Created by Melvin Biamont
 *
 * Datastore interface to access Tracks
 */
interface TrackDatastore {

    /**
     * Returns the top tracks using the given index
     */
    fun getTopTrack(index: Int): Single<TrackList>

    /**
     * Returns the searched tracks using the given query and index
     */
    fun searchTrack(query: String, index: Int): Single<TrackList>
}