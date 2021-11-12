package com.example.desafiofilmes.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val movieId: Int,
    @SerializedName("title") val movieTitle: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("tagline") val tagline : String?
)
