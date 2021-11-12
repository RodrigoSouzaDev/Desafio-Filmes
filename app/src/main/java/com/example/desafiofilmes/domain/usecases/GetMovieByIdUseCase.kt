package com.example.desafiofilmes.domain.usecases

import com.example.desafiofilmes.data.repository.MovieRepository
import com.example.desafiofilmes.model.Movie

class GetMovieByIdUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke (movieId: Int): Movie {
        return repository.getMovieById(movieId)
    }
}