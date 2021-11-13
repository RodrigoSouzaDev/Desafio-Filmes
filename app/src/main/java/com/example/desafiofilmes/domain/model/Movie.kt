package com.example.desafiofilmes.domain.model

data class Movie (
    val id: Int,
    val movieTitle: String,
    val genres: String,
    val popularity: String,
    val releaseDate: String,
    val voteCount: Int,
    val runtime: String,
    val posterPath: String?,
    var overview: String,
    val tagline: String
        )