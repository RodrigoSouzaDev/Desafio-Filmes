package com.example.desafiofilmes.data.repository

import com.example.desafiofilmes.model.Movie
import com.example.desafiofilmes.model.MovieList

interface MovieRepository {

    suspend fun getMovieById(movieId: Int):Movie
    suspend fun getSimilarMovies(movieId: Int):MovieList
}