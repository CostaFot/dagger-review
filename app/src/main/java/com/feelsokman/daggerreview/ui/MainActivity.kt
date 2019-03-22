package com.feelsokman.daggerreview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.feelsokman.daggerreview.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @JvmField
    @Inject
    var debugFlag: Boolean = false

    @Inject
    internal lateinit var factoryNowPlaying: NowPlayingMoviesViewModelFactory

    private lateinit var viewModelNowPlaying: NowPlayingMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelNowPlaying =
            ViewModelProviders.of(this, factoryNowPlaying)
                .get(NowPlayingMoviesViewModel::class.java)


        button.setOnClickListener { viewModelNowPlaying.getNowPlayingMovies() }
    }
}
