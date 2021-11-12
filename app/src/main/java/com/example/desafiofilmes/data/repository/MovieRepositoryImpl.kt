package com.example.desafiofilmes.data.repository

import com.example.desafiofilmes.model.Movie
import com.example.desafiofilmes.model.MovieList
import com.example.desafiofilmes.service.TmdbService

class MovieRepositoryImpl(private val service: TmdbService): MovieRepository {

    override suspend fun getMovieById(movieId: Int): Movie {
        return service.getMovieById(movieId)
    }

    override suspend fun getSimilarMovies(movieId: Int): MovieList {
        return service.getSimilarMovies(movieId)
    }
}