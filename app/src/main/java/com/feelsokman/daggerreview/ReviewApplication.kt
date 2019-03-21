package com.feelsokman.daggerreview

import com.feelsokman.daggerreview.di.AppComponent
import com.feelsokman.daggerreview.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class ReviewApplication : DaggerApplication() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber initialised")
        }
    }

    /**
    This is how most people do DI by having a static component
    and manually inject in the constructor / init
    if you do things properly you wouldn't need static component stuff
    but would get everything ready to use in the modules

    Provide everything in the modules ready to use is the best option then inject stuff into
    activities/fragments/viewmodels but you will see normal Java DI mostly in other projects
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
        return component
    }
}