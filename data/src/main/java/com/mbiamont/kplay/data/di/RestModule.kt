package com.mbiamont.kplay.data.di

import com.mbiamont.kplay.data.rest.TrackService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Melvin Biamont
 * Dagger2 Module to provides Rest dependencies
 */
@Module
class RestModule {

    @Singleton
    @Provides
    internal fun providesTrackService(): TrackService {
        return Retrofit.Builder()
                .baseUrl(TrackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(TrackService::class.java)
    }
}
