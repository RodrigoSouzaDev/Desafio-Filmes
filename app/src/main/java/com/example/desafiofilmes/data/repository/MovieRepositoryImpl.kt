package com.example.desafiofilmes.data.repository

import android.os.RemoteException
import com.example.desafiofilmes.data.service.TmdbService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MovieRepositoryImpl(private val service: TmdbService): MovieRepository {

    override suspend fun getMovieById(movieId: Int) = flow {
        try {
           val movie = service.getMovieById(movieId)
           emit(movie)
        }catch (ex: HttpException){
            throw RemoteException (ex.message ?: "Não foi possível fazer a busca no momento" )
        }
    }

    override suspend fun getSimilarMovies(movieId: Int)= flow {
        try {
            val movieListBody = service.getSimilarMovies(movieId)
            emit(movieListBody)
        }catch (ex: HttpException){
            throw RemoteException (ex.message ?: "Não foi possível fazer a busca no momento" )
        }
    }
}