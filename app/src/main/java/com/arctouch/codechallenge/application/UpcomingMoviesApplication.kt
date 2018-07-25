package com.arctouch.codechallenge.application

import com.arctouch.codechallenge.di.component.DaggerAppComponent
import com.github.ramonrabello.favoritehero.core.di.Injector
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class UpcomingMoviesApplication : DaggerApplication() {

    companion object {
        var instance: UpcomingMoviesApplication? = null
            private set
    }

    override fun applicationInjector(): AndroidInjector<UpcomingMoviesApplication> =
            DaggerAppComponent.builder().create(this@UpcomingMoviesApplication)

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        instance = this
    }
}