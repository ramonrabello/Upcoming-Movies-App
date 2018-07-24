package com.arctouch.codechallenge.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(movie: Movie) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie.releaseDate

            Glide.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])
}
