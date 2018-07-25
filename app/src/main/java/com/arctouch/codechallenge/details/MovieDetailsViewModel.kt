package com.arctouch.codechallenge.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.ktx.commonSubscribe
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.repository.remote.TmdbRemoteRepository
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * ViewModel for handling movie details related features.
 */
class MovieDetailsViewModel @Inject constructor(private val repository: TmdbRemoteRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val movieLiveData = MutableLiveData<Movie>()
    val errorMessageLiveData = MutableLiveData<Int>()

    fun loadMovieDetails(id:Long) {
        val disposable = repository.movie(id).commonSubscribe(
                        { response -> movieLiveData.postValue(response) }, // onNext()
                        { errorMessageLiveData.postValue(R.string.loading_movies_error) } // onError()
                )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}