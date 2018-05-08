package com.mbiamont.kplay.di

import com.deezer.sdk.network.connect.DeezerConnect
import com.mbiamont.kplay.domain.usecase.LoginUsecase
import com.mbiamont.kplay.domain.usecase.TrackUsecase
import com.mbiamont.kplay.presenter.LoginPresenter
import com.mbiamont.kplay.presenter.PlayerPresenter
import com.mbiamont.kplay.presenter.TracksPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresenterModule {

    @Singleton
    @Provides
    internal fun providesTracksPresenter(loginUsecase: LoginUsecase, trackUsecase: TrackUsecase): TracksPresenter {
        return TracksPresenter(loginUsecase, trackUsecase)
    }

    @Singleton
    @Provides
    internal fun providesLoginPresenter(loginUsecase: LoginUsecase): LoginPresenter {
        return LoginPresenter(loginUsecase)
    }

    @Singleton
    @Provides
    internal fun providesPlayerPresenter(deezerConnect: DeezerConnect): PlayerPresenter {
        return PlayerPresenter(deezerConnect)
    }
}