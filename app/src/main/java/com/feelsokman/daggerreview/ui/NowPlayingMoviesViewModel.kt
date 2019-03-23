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

/**
 * Instantiating this guy with the NowPlayingMoviesViewModelFactory
 * The factory comes in ready with everything bundled from the AppModule so have at it
 */
class NowPlayingMoviesViewModel(
    private val placeholderApi: MovieApi,
    private val apiKey: String,
    private val scheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var latestMoviesCall: Disposable? = null
    val moviesData = MutableLiveData<Movies>()

    fun getNowPlayingMovies() {

        latestMoviesCall?.dispose()

        latestMoviesCall = placeholderApi.getNowPlayingMovies(apiKey)
            .subscribeOn(scheduler)
            .doOnSubscribe { compositeDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    moviesData.postValue(it)
                    Timber.d("Success")
                },
                {
                    moviesData.postValue(null)
                    Timber.d("Oopsie doopsie")
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