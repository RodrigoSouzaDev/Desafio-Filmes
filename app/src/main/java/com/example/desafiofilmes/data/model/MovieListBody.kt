package com.example.desafiofilmes.data.model

import com.google.gson.annotations.SerializedName

data class MovieListBody(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieListItemBody>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)