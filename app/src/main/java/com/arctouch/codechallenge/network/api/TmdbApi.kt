package com.arctouch.codechallenge.network.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("genre/movie/list")
    fun genres(): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(@Query("page") page: Long = 1): Observable<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(@Path("id") id: Int): Observable<Movie>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Observable<UpcomingMoviesResponse>
}
