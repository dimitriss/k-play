package com.mbiamont.kplay

import android.app.Activity
import android.app.Application
import com.mbiamont.kplay.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Melvin Biamont
 *
 * K-Play application implementation
 */
class KplayApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatcher: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatcher
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        DaggerAppComponent
                .builder()
                .application(this)
                .context(this)
                .build()
                .inject(this)
    }
}