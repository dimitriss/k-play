package com.mbiamont.kplay.di

import com.mbiamont.kplay.activity.LoginActivity
import com.mbiamont.kplay.activity.TracksActivity
import com.mbiamont.kplay.activity.PlayerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Melvin Biamont
 *
 * Dagger2 Module to provide Activities dependencies
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): TracksActivity

    @ContributesAndroidInjector
    internal abstract fun playerActivity(): PlayerActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity
}
