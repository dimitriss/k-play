package com.mbiamont.kplay.domain.usecase

import com.mbiamont.kplay.data.model.TrackList
import com.mbiamont.kplay.data.repository.TrackRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * Usecase to search or retrieve the tracks
 */
class TrackUsecase @Inject constructor(private val trackRepository: TrackRepository) {

    /**
     * Returns the top tracks using the given index
     */
    fun getTopTrack(index: Int): Single<TrackList> {
        return trackRepository.getTopTrack(index)
    }

    /**
     * Returns the searched tracks using the given query and index
     */
    fun searchTrack(query: String, index: Int): Single<TrackList> {
        return trackRepository.searchTrack(query, index)
    }
}