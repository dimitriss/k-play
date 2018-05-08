package com.mbiamont.kplay.domain.usecase

import com.mbiamont.kplay.data.model.TrackList
import com.mbiamont.kplay.data.repository.TrackRepository
import io.reactivex.Single
import javax.inject.Inject

class TrackUsecase @Inject constructor(private val trackRepository: TrackRepository) {

    fun getTopTrack(index: Int): Single<TrackList> {
        return trackRepository.getTopTrack(index)
    }

    fun searchTrack(query: String, index: Int): Single<TrackList> {
        return trackRepository.searchTrack(query, index)
    }
}