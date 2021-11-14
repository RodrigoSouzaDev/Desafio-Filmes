package com.example.desafiofilmes.data.repository

import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.data.model.MovieListBody
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieById(movieId: Int):Flow<MovieBody>
    suspend fun getSimilarMovies(movieId: Int):Flow<MovieListBody>
}