package com.feelsokman.daggerreview

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("dates")
    val dates: Any?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Any>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)