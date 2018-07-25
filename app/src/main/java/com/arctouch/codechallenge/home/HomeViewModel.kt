package com.arctouch.codechallenge.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.repository.remote.TmdbRemoteRepository
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * ViewModel for handling home screen related features.
 */
class HomeViewModel @Inject constructor(private val repository: TmdbRemoteRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val allMoviesLiveData = MutableLiveData<List<Movie>>()
    val loadingMoreLiveData = MutableLiveData<Boolean>()
    val searchingLiveData = MutableLiveData<Boolean>()
    val errorMessageLiveData = MutableLiveData<Int>()
    private val publishSubject = PublishSubject.create<String>()
    private var currentPage = 1L


    fun loadUpcomingMovies() {
        if (currentPage > 1) {
            loadingMoreLiveData.postValue(true)
        }

        repository.genres()
                .flatMap { genreResponse -> Observable.just(genreResponse.genres) }

                // save all genres first to be used later by upcoming events observable
                .map { genres -> Cache.cacheGenres(genres) }
                .concatMap { repository.upcomingMovies(currentPage) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            val moviesWithGenres = response.results.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            allMoviesLiveData.postValue(moviesWithGenres)
                            loadingMoreLiveData.postValue(false)
                        }, // onNext()
                        { errorMessageLiveData.postValue(R.string.loading_movies_error) } // onError()
                )
    }

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            searchingLiveData.postValue(true)
            val disposable = publishSubject.debounce(500, TimeUnit.MILLISECONDS)
                    .filter { queryString -> queryString.length > 3 }
                    .distinctUntilChanged()
                    .switchMap { queryString ->
                        repository.searchMovies(queryString)
                                .subscribeOn(Schedulers.io())
                    } //  to avoid InterruptedIOException
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<UpcomingMoviesResponse>() {
                        override fun onComplete() {}
                        override fun onNext(response: UpcomingMoviesResponse) {
                            allMoviesLiveData.postValue(response.results)
                            searchingLiveData.postValue(false)
                        }

                        override fun onError(e: Throwable?) {
                            errorMessageLiveData.postValue(R.string.searching_movie_error)
                            searchingLiveData.postValue(false)
                        }
                    })
            publishSubject.onNext(query)
            compositeDisposable.add(disposable)
        }
    }

    fun paginate() {
        currentPage = currentPage.plus(1)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}