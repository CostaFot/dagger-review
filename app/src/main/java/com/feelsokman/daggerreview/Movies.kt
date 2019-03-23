package com.feelsokman.daggerreview

import com.google.gson.annotations.SerializedName

/**
 * The class representing the Json response. Use http://www.jsonschema2pojo.org/ to get this.
 * Or you can add this plugin for AS here https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
 * It will create your data class from JSON to kotlin.
 *
 * Just writing "Any" cause I'm a terrible human being and don't care about the JSON in this case
 */
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