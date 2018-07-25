package com.arctouch.codechallenge.data.repository.remote

import com.arctouch.codechallenge.network.api.TmdbApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [TmdbRemoteRepository] class.
 */
class TmdbRemoteRepositoryTest {

    @Mock
    lateinit var marvelApi: TmdbApi
    private lateinit var repository: TmdbRemoteRepository

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        repository = TmdbRemoteRepository(marvelApi)
    }

    @Test
    fun `should verify if all upcoming movies were retrieved`() {
        val currentPage = 1L
        repository.upcomingMovies(currentPage)
        verify(marvelApi).upcomingMovies(currentPage)
    }

    @Test
    fun `should verify if movies were searched`() {
        repository.searchMovies("Mission Impossible")
        verify(marvelApi).searchMovies("Mission Impossible")
    }

    @Test
    fun `should verify if all genres were retrieved`() {
        repository.genres()
        verify(marvelApi).genres()
    }
}