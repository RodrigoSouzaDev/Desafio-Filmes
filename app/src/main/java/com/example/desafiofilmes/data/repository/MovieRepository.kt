package com.example.desafiofilmes.data.repository

import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.data.model.MovieList

interface MovieRepository {

    suspend fun getMovieById(movieId: Int):MovieBody
    suspend fun getSimilarMovies(movieId: Int):MovieList
}