package com.feelsokman.daggerreview.di

import android.app.Application
import android.content.Context
import com.feelsokman.daggerreview.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        const val BASE_URL = "key.base.url"
        const val API_KEY = "key.api.key"
    }

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    fun providesDebugFlag(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    fun providesExecutionScheduler() = Schedulers.io()
}