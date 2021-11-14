package com.example.desafiofilmes.data.model

import com.google.gson.annotations.SerializedName

data class MovieListItemBody (
    @SerializedName("id") val id:Int,
    @SerializedName("title") val title: String,
    @SerializedName("realese_date") val releaseDate: String?,
    @SerializedName("genre_ids") val genres: List<Int>,
    @SerializedName("overview") val overview:String,
    @SerializedName("poster_path") val posterPath: String?
        )