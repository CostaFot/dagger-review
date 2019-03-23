package com.feelsokman.daggerreview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.feelsokman.daggerreview.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    // field injection for primitive stuff needs @JvmField !!
    // this is written as an example and not needed cause it's a frequent question in StackOverflow
    @JvmField
    @Inject
    var debugFlag: Boolean = false

    // this guy is injected with the providesViewModelFactory method that was declared in AppModule
    @Inject
    internal lateinit var factoryNowPlaying: NowPlayingMoviesViewModelFactory

    // no need for injection with this variable
    private lateinit var viewModelNowPlaying: NowPlayingMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // this is needed when working with Android specific stuff (activities/fragments/etc)!
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // instantiating our ViewModel with the injected factory
        viewModelNowPlaying =
            ViewModelProviders.of(this, factoryNowPlaying)
                .get(NowPlayingMoviesViewModel::class.java)

        // observing changes in moviesData for fun, don't have to do this
        viewModelNowPlaying.moviesData.observe(this,
            Observer { movies ->
                movies?.let {
                    textView.text = movies.toString()
                }
                    ?: run {
                        textView.text = "oopsie doopsie something went wrong"
                    }

            })

        // click listener to execute the API call manually and test things out
        button.setOnClickListener { viewModelNowPlaying.getNowPlayingMovies() }
    }
}
