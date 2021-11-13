package com.example.desafiofilmes.domain.usecases

import com.example.desafiofilmes.data.repository.MovieRepository
import com.example.desafiofilmes.data.model.MovieList

class GetSimilarMoviesUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): MovieList {
        return repository.getSimilarMovies(movieId)
    }
}