package com.mbiamont.kplay.domain.di

import com.deezer.sdk.network.connect.DeezerConnect
import com.mbiamont.kplay.data.repository.TrackRepository
import com.mbiamont.kplay.domain.usecase.LoginUsecase
import com.mbiamont.kplay.domain.usecase.TrackUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Melvin Biamont
 *
 * Dagger2 Module to provides Usecases dependencies
 */
@Module
class UsecaseModule {

    @Singleton
    @Provides
    internal fun providesLoginUsecase(deezerConnect: DeezerConnect): LoginUsecase {
        return LoginUsecase(deezerConnect)
    }

    @Singleton
    @Provides
    internal fun providesTrackUsecase(trackRepository: TrackRepository): TrackUsecase {
        return TrackUsecase(trackRepository)
    }
}