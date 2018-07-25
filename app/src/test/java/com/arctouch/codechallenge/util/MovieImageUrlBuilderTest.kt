package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.network.RequestParams
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [MovieImageUrlBuilder] class.
 */
class MovieImageUrlBuilderTest {

    @Test
    fun buildPosterUrl() {
        val posterPath = "https://image.tmdb.org/t/p/w154/image.jpeg"
        val expected = "${BuildConfig.POSTER_URL}$posterPath?${RequestParams.API_KEY}=${BuildConfig.API_KEY}"
        val actual = MovieImageUrlBuilder.buildPosterUrl(posterPath)
        assertEquals(expected, actual)
    }

    @Test
    fun buildBackdropUrl() {
        val posterPath = "https://image.tmdb.org/t/p/w780/image.jpeg"
        val expected = "${BuildConfig.BACKDROP_URL}$posterPath?${RequestParams.API_KEY}=${BuildConfig.API_KEY}"
        val actual = MovieImageUrlBuilder.buildBackdropUrl(posterPath)
        assertEquals(expected, actual)
    }
}