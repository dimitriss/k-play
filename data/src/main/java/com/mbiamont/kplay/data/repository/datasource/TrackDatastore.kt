package com.mbiamont.kplay.data.repository.datasource

import com.mbiamont.kplay.data.model.TrackList
import io.reactivex.Single

interface TrackDatastore {

    fun getTopTrack(index: Int): Single<TrackList>

    fun searchTrack(query: String, index: Int): Single<TrackList>
}