package com.mbiamont.kplay.di

import android.app.Application
import android.content.Context
import com.mbiamont.kplay.KplayApplication
import com.mbiamont.kplay.data.di.RepositoryModule
import com.mbiamont.kplay.data.di.RestModule
import com.mbiamont.kplay.domain.di.DeezerModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DeezerModule::class,
    ActivityModule::class,
    RestModule::class,
    RepositoryModule::class,
    PresenterModule::class])
interface AppComponent {

    fun inject(application: KplayApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}