package com.feelsokman.daggerreview

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class MovieApi(retrofit: Retrofit) {

    private val api: Api = retrofit.create(Api::class.java)

    fun getNowPlayingMovies(apiKey: String) = api.getNowPlayingMovies(apiKey)

    interface Api {

        @GET("movie/now_playing")
        fun getNowPlayingMovies(@Query("api_key") apiKey: String): Single<Movies>
    }
}