package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.api.TmdbApi

object MovieImageUrlBuilder {

    private val POSTER_URL = "https://image.tmdb.org/t/p/w154"
    private val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

    fun buildPosterUrl(posterPath: String?): String {
        return POSTER_URL + posterPath + "?api_key=" + TmdbApi.API_KEY
    }

    fun buildBackdropUrl(backdropPath: String?): String {
        return BACKDROP_URL + backdropPath + "?api_key=" + TmdbApi.API_KEY
    }
}
