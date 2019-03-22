package com.feelsokman.daggerreview.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.feelsokman.daggerreview.MovieApi
import com.feelsokman.daggerreview.Movies
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class NowPlayingMoviesViewModel(
    private val placeholderApi: MovieApi,
    private val apiKey: String,
    private val scheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var latestMoviesCall: Disposable? = null
    val moviesData = MutableLiveData<List<Any>>()

    // a class to wrap around the response to make things easier later
    inner class Result(val movies: List<Movies>? = null, val errorMessage: String? = null) {

        fun hasMovies(): Boolean {
            return movies != null && !movies.isEmpty()
        }

        fun hasError(): Boolean {
            return errorMessage != null
        }
    }

    fun getNowPlayingMovies() {
    }

    private fun cancelAllJobs() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }
}