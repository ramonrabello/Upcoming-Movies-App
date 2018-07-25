package com.arctouch.codechallenge.data.repository.remote

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.network.api.TmdbApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Remote repository that calls TMDb API endpoints.
 */
class TmdbRemoteRepository @Inject constructor(private val api: TmdbApi) {
    fun genres() = api.genres()
    fun upcomingMovies(page:Long) = api.upcomingMovies(page)
    fun searchMovies(query:String) = api.searchMovies(query)
    fun movie(id:Long) : Observable<Movie> = api.movie(id)
}