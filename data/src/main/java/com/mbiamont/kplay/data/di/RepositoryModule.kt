package com.mbiamont.kplay.data.di

import com.mbiamont.kplay.data.repository.TrackRepository
import com.mbiamont.kplay.data.repository.datasource.TrackRestDatastore
import com.mbiamont.kplay.data.rest.TrackService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Melvin Biamont
 * Dagger2 Module to provides Repository dependencies
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    internal fun providesTrackRestDatastore(trackService: TrackService): TrackRestDatastore {
        return TrackRestDatastore(trackService)
    }

    @Singleton
    @Provides
    internal fun providesTrackRepository(trackRestDatastore: TrackRestDatastore): TrackRepository {
        return TrackRepository(trackRestDatastore)
    }
}
