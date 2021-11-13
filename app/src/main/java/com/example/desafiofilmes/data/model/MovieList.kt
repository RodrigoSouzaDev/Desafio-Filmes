package com.example.desafiofilmes.data.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieBody>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)