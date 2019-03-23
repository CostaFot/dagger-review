package com.feelsokman.daggerreview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feelsokman.daggerreview.MovieApi
import io.reactivex.Scheduler

/**
 * Copy pasta how to ViewModelFactory
 * Google says you cannot pass stuff in the ViewModel constructor like normal
 * Just do this I guess, who am I to judge
 */
class NowPlayingMoviesViewModelFactory(
    private val placeholderApi: MovieApi,
    private val apiKey: String,
    private val scheduler: Scheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return NowPlayingMoviesViewModel(
            placeholderApi,
            apiKey,
            scheduler
        ) as T
    }
}