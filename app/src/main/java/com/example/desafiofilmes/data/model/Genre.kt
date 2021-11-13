package com.example.desafiofilmes.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val genreId: Int,
    @SerializedName("name") val genreName: String
)
