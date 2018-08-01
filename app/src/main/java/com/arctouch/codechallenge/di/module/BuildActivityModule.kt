package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.details.MovieDetailsActivity
import com.arctouch.codechallenge.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMovieDetailsActivity(): MovieDetailsActivity
}