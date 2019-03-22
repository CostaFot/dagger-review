package com.feelsokman.daggerreview.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.feelsokman.daggerreview.MovieApi
import com.feelsokman.daggerreview.Movies
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class NowPlayingMoviesViewModel(
    private val placeholderApi: MovieApi,
    private val apiKey: String,
    private val scheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var latestMoviesCall: Disposable? = null
    val moviesData = MutableLiveData<Movies>()

    // a class to wrap around the response to make things easier later
    inner class Result(val listOfMovies: Movies? = null, val errorMessage: String? = null) {

        fun hasMovies(): Boolean {
            return listOfMovies != null
        }

        fun hasError(): Boolean {
            return errorMessage != null
        }
    }

    fun getNowPlayingMovies() {

        latestMoviesCall?.dispose()

        latestMoviesCall = placeholderApi.getNowPlayingMovies(apiKey)
            .subscribeOn(scheduler)
            .doOnSubscribe { compositeDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .map { movies: Movies -> Result(listOfMovies = movies) }
            .subscribe(
                {
                    Timber.d("Success")
                },
                {
                    Timber.d("Failure")
                }
            )

    }

    private fun cancelAllJobs() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }
}