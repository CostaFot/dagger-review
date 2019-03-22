package com.feelsokman.daggerreview.di

import android.app.Application
import android.content.Context
import com.feelsokman.daggerreview.BuildConfig
import com.feelsokman.daggerreview.MovieApi
import com.feelsokman.daggerreview.ui.NowPlayingMoviesViewModelFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        const val NAME_URL = "key.base.url"
        const val API_KEY = "key.api.key"
    }

    @Provides
    @Singleton
    internal fun providesCache(context: Context): Cache {
        return Cache(context.cacheDir, 10 * 1024 * 1024)
    }

    @Provides
    @Singleton
    internal fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    internal fun providesDebugFlag(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    @Named(NAME_URL)
    internal fun providesBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Named(API_KEY)
    internal fun providesApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    internal fun providesGson(): Gson {
        return GsonBuilder().setPrettyPrinting().create()
    }

    @Provides
    internal fun providesExecutionScheduler() = Schedulers.io()

    @Provides
    @Singleton
    internal fun providesOkHttpClient(
        cache: Cache,
        isDebugEnabled: Boolean
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient().newBuilder()
        val loggingInterceptor = HttpLoggingInterceptor()
        val level = when {
            isDebugEnabled -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        loggingInterceptor.level = level
        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.cache(cache)
        return okHttpBuilder.build()
    }

    @Provides
    internal fun providesRetrofit(
        @Named(NAME_URL) baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    internal fun providesMovieApi(retrofit: Retrofit) = MovieApi(retrofit)

    // everything we want in here is declared as @Provides further up so no need to do anything else
    // Dagger will do things magically
    @Provides
    internal fun providesViewModelFactory(
        movieApi: MovieApi,
        @Named(API_KEY) apiKey: String,
        scheduler: Scheduler
    ) = NowPlayingMoviesViewModelFactory(movieApi, apiKey, scheduler)
}