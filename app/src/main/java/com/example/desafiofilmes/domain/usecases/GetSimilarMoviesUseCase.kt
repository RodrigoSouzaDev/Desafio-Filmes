package com.example.desafiofilmes.domain.usecases

import com.example.desafiofilmes.core.UseCase
import com.example.desafiofilmes.data.repository.MovieRepository
import com.example.desafiofilmes.domain.model.MovieListItem
import com.example.desafiofilmes.util.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetSimilarMoviesUseCase(private val repository: MovieRepository) :
    UseCase<Int, List<MovieListItem>>() {

    override suspend fun execute(param: Int): Flow<List<MovieListItem>> {
        val movies : ArrayList<MovieListItem> = ArrayList()
        val similarMovies = repository.getSimilarMovies(param)
        val similarMoviesList = similarMovies.first().movies
        for (movie in similarMoviesList)
        {
            movies.add(movie.toModel())
        }

        return flow {
            emit(movies)
        }
    }
}