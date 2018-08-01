package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.network.RequestParams

object MovieImageUrlBuilder {

    fun buildPosterUrl(posterPath: String?): String {
        return "${BuildConfig.POSTER_URL}$posterPath?${RequestParams.API_KEY}=${BuildConfig.API_KEY}"
    }

    fun buildBackdropUrl(backdropPath: String?): String {
        return "${BuildConfig.BACKDROP_URL}$backdropPath?${RequestParams.API_KEY}=${BuildConfig.API_KEY}"
    }
}
