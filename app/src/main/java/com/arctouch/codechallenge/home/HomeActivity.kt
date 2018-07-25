package com.arctouch.codechallenge.home

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.ktx.obtainViewModel
import com.arctouch.codechallenge.model.Movie
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_details.app_bar
import kotlinx.android.synthetic.main.home_activity.load_more_progress
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView
import kotlinx.android.synthetic.main.home_activity.searching_progress_bar
import javax.inject.Inject

/**
 * Activity that displays the list of upcoming movies.
 */
class HomeActivity : AppCompatActivity() {

    @Inject
    @VisibleForTesting
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchView: SearchView
    private val endlessScrollRecyclerListener by lazy { EndlessScrollListener(recyclerView.layoutManager) }
    private val homeAdapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        recyclerView.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(endlessScrollRecyclerListener)
        }
        viewModel = obtainViewModel(viewModelFactory, HomeViewModel::class.java)
        initObservers()
        viewModel.loadUpcomingMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                searchMovies(queryText)
                return false
            }

            override fun onQueryTextChange(queryText: String): Boolean {
                searchMovies(queryText)
                return false
            }
        })

        searchView.setOnCloseListener {
            homeAdapter.clear()
            endlessScrollRecyclerListener.resetState()
            viewModel.loadUpcomingMovies()
            false
        }
        searchView.setIconifiedByDefault(true)
        return true
    }

    private fun searchMovies(queryText: String) {
        if (queryText.isNotEmpty()){
            endlessScrollRecyclerListener.resetState()
            homeAdapter.clear()
            viewModel.searchMovies(queryText)
        } else {
            endlessScrollRecyclerListener.resetState()
            homeAdapter.clear()
            viewModel.loadUpcomingMovies()
        }

    }

    private fun initObservers() {
        viewModel.allMoviesLiveData.observe(this, Observer<List<Movie>> { movies ->
            movies?.let {
                homeAdapter.addMovies(it)
                progressBar.visibility = View.GONE
            }
        })
        viewModel.loadingMoreLiveData.observe(this, Observer<Boolean> { isLoadingMore ->
            isLoadingMore?.let {
                load_more_progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.searchingLiveData.observe(this, Observer<Boolean> { showSearchingProgress ->
            showSearchingProgress?.let {
                searching_progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.errorMessageLiveData.observe(this, Observer<Int> { resId ->
            resId?.let{
                progressBar.visibility = View.GONE
                searching_progress_bar.visibility = View.GONE
                load_more_progress.visibility = View.GONE
                Snackbar.make(recyclerView, getString(it), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    inner class EndlessScrollListener(layoutManager: RecyclerView.LayoutManager) : EndlessScrollRecyclerListener(layoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            viewModel.paginate()
            viewModel.loadUpcomingMovies()
        }
    }
}
