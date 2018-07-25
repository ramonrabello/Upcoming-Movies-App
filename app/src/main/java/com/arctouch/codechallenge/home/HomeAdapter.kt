package com.arctouch.codechallenge.home

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.ktx.toTypeface
import com.arctouch.codechallenge.details.MovieDetailsActivity
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.content_movie_details.movie_genres
import kotlinx.android.synthetic.main.content_movie_details.movie_name
import kotlinx.android.synthetic.main.content_movie_details.movie_release_date
import kotlinx.android.synthetic.main.movie_item.view.*

/**
 * Adapter that holds movie items.
 */
class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val movies = mutableListOf<Movie>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.apply {
                titleTextView.text = movie.title
                genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
                releaseDateTextView.text = movie.releaseDate
                Glide.with(itemView.context)
                        .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                        .into(posterImageView)

                setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie)
                    context.startActivity(intent)
                }

                titleTextView.toTypeface("OpenSans-SemiBold")
                releaseDateTextView.toTypeface("OpenSans-Regular")
                genresTextView.toTypeface("OpenSans-Light")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    fun addMovies(upcomingMovies:List<Movie>){
        val positionStart = movies.size.plus(1)
        movies.addAll(upcomingMovies)
        notifyItemRangeChanged(positionStart, upcomingMovies.size)
    }
}
