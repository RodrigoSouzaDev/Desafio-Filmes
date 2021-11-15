package com.example.desafiofilmes.data.model

import com.google.gson.annotations.SerializedName

data class MovieBody(
    @SerializedName("id") val movieId: Int,
    @SerializedName("title") val movieTitle: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("popularity") val views: Double,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_count") val likes: Int,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("overview") val overview: String?,
)
