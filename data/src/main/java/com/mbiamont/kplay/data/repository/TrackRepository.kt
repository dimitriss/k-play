package com.mbiamont.kplay.data.repository

import com.mbiamont.kplay.data.model.TrackList
import com.mbiamont.kplay.data.repository.datasource.TrackRestDatastore
import io.reactivex.Single
import javax.inject.Inject

class TrackRepository @Inject constructor(private val trackRestDatastore: TrackRestDatastore) {

    fun getTopTrack(index: Int): Single<TrackList> {
        return trackRestDatastore.getTopTrack(index)
    }

    fun searchTrack(query: String, index: Int): Single<TrackList> {
        return trackRestDatastore.searchTrack(query, index)
    }
}