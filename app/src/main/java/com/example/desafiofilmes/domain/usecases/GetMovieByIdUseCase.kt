package com.example.desafiofilmes.domain.usecases

import com.example.desafiofilmes.core.UseCase
import com.example.desafiofilmes.core.extensions.toModel
import com.example.desafiofilmes.data.repository.MovieRepository
import com.example.desafiofilmes.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetMovieByIdUseCase (private val repository: MovieRepository) : UseCase<Int, Movie>() {

    override suspend fun execute(param: Int): Flow<Movie> {
        val movie = repository.getMovieById(param)
        val movieModel = movie.first().toModel()

        return flow {
            emit(movieModel)
        }
    }
}