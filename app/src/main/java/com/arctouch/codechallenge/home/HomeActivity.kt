package com.arctouch.codechallenge.home

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.data.Cache
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView

class HomeActivity : BaseActivity() {

    private var currentPage:Long = 1
    private val adapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }

    override fun onResume() {
        super.onResume()
        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, currentPage, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    recyclerView.adapter = adapter
                    recyclerView.addOnScrollListener(EndlessScrollListener(recyclerView.layoutManager))
                    adapter.addMovies(moviesWithGenres)
                    progressBar.visibility = View.GONE
                }
    }

    inner class EndlessScrollListener(layoutManager: RecyclerView.LayoutManager) : EndlessScrollRecyclerListener(layoutManager){
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            currentPage = currentPage.plus(1)
            api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, currentPage, TmdbApi.DEFAULT_REGION)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val newMovies = it.results.map { movie ->
                            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                        }
                        adapter.addMovies(newMovies)
                    }
        }
    }
}
