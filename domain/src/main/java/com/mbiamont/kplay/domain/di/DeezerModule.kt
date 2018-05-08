package com.mbiamont.kplay.domain.di

import android.content.Context
import com.deezer.sdk.network.connect.DeezerConnect
import com.mbiamont.kplay.domain.utils.DEEZER_ID
import dagger.Module
import dagger.Provides

@Module
class DeezerModule {

    @Provides
    internal fun providesDeezerConnect(context: Context): DeezerConnect {
        return DeezerConnect.forApp(DEEZER_ID).withContext(context).build()
    }


}
