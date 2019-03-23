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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Declare everything here. Go wild!
 * You can learn how to do loads of modules, scopes and other nonsense later.
 */
@Module
class AppModule {

    // Some static strings declared here to give names to some variables below so they are orphans
    companion object {
        const val NAME_URL = "key.base.url"
        const val API_KEY = "key.api.key"
    }

    // this is quite good to have when working with Retrofit and does some magic :)
    @Provides
    @Singleton
    internal fun providesCache(context: Context): Cache {
        return Cache(context.cacheDir, 10 * 1024 * 1024)
    }

    // Application context. Who knows where you might need it!
    @Provides
    @Singleton
    internal fun providesContext(application: Application): Context {
        return application
    }

    // A-ha! Debug flag, can prove useful
    @Provides
    internal fun providesDebugFlag(): Boolean {
        return BuildConfig.DEBUG
    }

    /**
     *   The URL of the movieDB endpoint - declared in BuildConfig normally.
     *   You can write it here as a simple string hardcoded too
     *   Annotating with "@Named" cause we have multiple strings provided in this file
     *   We want Dagger to know which String to choose from!
     */
    @Provides
    @Named(NAME_URL)
    internal fun providesBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    // The API key provided by movieDB when registering, declared in BuildConfig.
    // You can write it here as a simple string hardcoded too
    @Provides
    @Named(API_KEY)
    internal fun providesApiKey(): String {
        return BuildConfig.API_KEY
    }

    // Trusty Gson to makes things work with Retrofit
    @Provides
    internal fun providesGson(): Gson {
        return GsonBuilder().setPrettyPrinting().create()
    }

    // IO scheduler so we can do some RxJava stuff later
    @Provides
    internal fun providesExecutionScheduler() = Schedulers.io()

    /*
    A bunch of stuff was declared above this line.
    Everything below is going to be using that stuff so just go ahead and assume you got it.
    Dagger will take care of providing them for the next few methods.
     */

    /**
     * Our OkHttpclient need an instance of Cache and a Boolean for debug.
     * They are all provided and taken care of above so just pass them in
     * It seems completely illogical and counter-intuitive when you first see this but just roll with it
     */
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

    /**
     * Same things as the OkHttpClient applies here.
     * "@Named" specifies which string to get from the 2 declared in this file
     * In this case the endpoint NAME_URL
     */
    @Provides
    internal fun providesRetrofit(
        @Named(NAME_URL) baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * This guy needs an instance of Retrofit
     * It's provided right above it so ez pz
     */
    @Provides
    internal fun providesMovieApi(retrofit: Retrofit) = MovieApi(retrofit)

    /**
     * We can pass arguments in a ViewModel with a ViewModelFactory
     * We need the MovieApi to perform the API call with Retrofit
     * The api key declared in BuildConfig so movieDB can accept our request
     * The IO scheduler cause all API calls should happen in a background thread.
     *
     * We are going to field inject this into the MainActivity
     */
    @Provides
    internal fun providesViewModelFactory(
        movieApi: MovieApi,
        @Named(API_KEY) apiKey: String,
        scheduler: Scheduler
    ) = NowPlayingMoviesViewModelFactory(movieApi, apiKey, scheduler)
}