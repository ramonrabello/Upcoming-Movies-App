package com.arctouch.codechallenge.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.ktx.obtainViewModel
import com.arctouch.codechallenge.core.ktx.toTypeface
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_details.coordinator_layout
import kotlinx.android.synthetic.main.activity_movie_details.fab
import kotlinx.android.synthetic.main.activity_movie_details.movie_backdrop_image
import kotlinx.android.synthetic.main.activity_movie_details.toolbar
import kotlinx.android.synthetic.main.activity_movie_details.toolbar_layout
import kotlinx.android.synthetic.main.content_movie_details.movie_genres
import kotlinx.android.synthetic.main.content_movie_details.movie_name
import kotlinx.android.synthetic.main.content_movie_details.movie_overview
import kotlinx.android.synthetic.main.content_movie_details.movie_poster_image
import kotlinx.android.synthetic.main.content_movie_details.movie_release_date
import javax.inject.Inject

/**
 * Activity that displays movie details.
 */
class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    @VisibleForTesting
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailsViewModel

    companion object {
        const val EXTRA_MOVIE_ID = "com.arctouch.codechallenge.details.EXTRA_MOVIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_layout.setExpandedTitleColor(Color.TRANSPARENT)
        viewModel = obtainViewModel(viewModelFactory, MovieDetailsViewModel::class.java)
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        initObservers()
        viewModel.loadMovieDetails(movieId)

        // customize typefaces
        movie_name.toTypeface("OpenSans-SemiBold")
        movie_release_date.toTypeface("OpenSans-Regular")
        movie_genres.toTypeface("OpenSans-Light")
        movie_overview.toTypeface("OpenSans-Light")
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

    private fun initObservers() {
        viewModel.movieLiveData.observe(this, Observer<Movie> { movie ->
            movie?.let {
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
                }

                fab.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, movie.id))
                    startActivity(Intent.createChooser(intent, getString(R.string.intent_chooser_title)))
                }
            }
        })
        viewModel.errorMessageLiveData.observe(this, Observer<Int> { messageResId ->
            messageResId?.let { Snackbar.make(coordinator_layout, getString(it), Snackbar.LENGTH_SHORT).show() }
        })
    }
}
