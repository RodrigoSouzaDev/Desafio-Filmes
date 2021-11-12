package com.example.desafiofilmes.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val genreId: Int,
    @SerializedName("name") val genreName: String
)
