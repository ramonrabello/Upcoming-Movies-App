package com.arctouch.codechallenge.di.component

import com.arctouch.codechallenge.application.UpcomingMoviesApplication
import com.arctouch.codechallenge.di.module.AppModule
import com.arctouch.codechallenge.di.module.BuildActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 *
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    BuildActivityModule::class])
interface AppComponent : AndroidInjector<UpcomingMoviesApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<UpcomingMoviesApplication>()
}