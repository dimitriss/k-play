package com.mbiamont.kplay.data.repository

import com.mbiamont.kplay.data.model.TrackList
import com.mbiamont.kplay.data.repository.datasource.TrackRestDatastore
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Repository to access tracks
 */
class TrackRepository @Inject constructor(private val trackRestDatastore: TrackRestDatastore) {

    /**
     * Returns the top tracks using the given index
     */
    fun getTopTrack(index: Int): Single<TrackList> {
        return trackRestDatastore.getTopTrack(index)
    }

    /**
     * Returns the searched tracks using the given query and index
     */
    fun searchTrack(query: String, index: Int): Single<TrackList> {
        return trackRestDatastore.searchTrack(query, index)
    }
}