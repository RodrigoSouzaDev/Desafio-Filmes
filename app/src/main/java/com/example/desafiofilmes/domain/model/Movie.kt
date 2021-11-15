package com.example.desafiofilmes.domain.model

data class Movie (
    val id: Int,
    val movieTitle: String,
    val genres: String,
    val views: String,
    val releaseDate: String,
    val likes: String,
    val runtime: String,
    val posterPath: String?,
    var overview: String,
        )