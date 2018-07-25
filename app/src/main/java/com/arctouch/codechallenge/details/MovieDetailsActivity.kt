package com.arctouch.codechallenge.details

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.ktx.toTypeface
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.fab
import kotlinx.android.synthetic.main.activity_movie_details.movie_backdrop_image
import kotlinx.android.synthetic.main.activity_movie_details.toolbar
import kotlinx.android.synthetic.main.content_movie_details.movie_genres
import kotlinx.android.synthetic.main.content_movie_details.movie_name
import kotlinx.android.synthetic.main.content_movie_details.movie_overview
import kotlinx.android.synthetic.main.content_movie_details.movie_poster_image
import kotlinx.android.synthetic.main.content_movie_details.movie_release_date

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "com.arctouch.codechallenge.details.EXTRA_MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie.apply {
            Glide.with(this@MovieDetailsActivity)
                    .load(MovieImageUrlBuilder.buildBackdropUrl(movie.backdropPath))
                    .into(movie_backdrop_image)
            Glide.with(this@MovieDetailsActivity)
                    .load(MovieImageUrlBuilder.buildPosterUrl(movie.posterPath))
                    .into(movie_poster_image)
            movie_name.text = movie.title
            movie_release_date.text = movie.releaseDate
            movie_genres.text = movie.genres?.joinToString(separator = ", ") { it.name }
            movie_overview.text = movie.overview

            movie_name.toTypeface("OpenSans-SemiBold")
            movie_release_date.toTypeface("OpenSans-Regular")
            movie_genres.toTypeface("OpenSans-Light")
            movie_overview.toTypeface("OpenSans-Light")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
