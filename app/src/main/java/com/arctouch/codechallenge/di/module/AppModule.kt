package com.arctouch.codechallenge.di.module

import android.app.Application
import android.content.Context
import com.arctouch.codechallenge.application.UpcomingMoviesApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: UpcomingMoviesApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideApplication(application: UpcomingMoviesApplication): Application {
        return application
    }
}