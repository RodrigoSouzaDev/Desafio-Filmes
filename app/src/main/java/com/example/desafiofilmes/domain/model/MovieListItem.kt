package com.example.desafiofilmes.domain.model

data class MovieListItem (
    val id: Int,
    val title: String,
    val genres: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String?
    )