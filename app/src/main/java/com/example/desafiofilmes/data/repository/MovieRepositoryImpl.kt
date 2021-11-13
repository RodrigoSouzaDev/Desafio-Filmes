package com.example.desafiofilmes.data.repository

import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.data.model.MovieList
import com.example.desafiofilmes.data.service.TmdbService

class MovieRepositoryImpl(private val service: TmdbService): MovieRepository {

    override suspend fun getMovieById(movieId: Int): MovieBody {
        return service.getMovieById(movieId)
    }

    override suspend fun getSimilarMovies(movieId: Int): MovieList {
        return service.getSimilarMovies(movieId)
    }
}