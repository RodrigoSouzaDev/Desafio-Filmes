package com.example.desafiofilmes.data.service

import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.data.model.MovieListBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: Int,
        @Query("api_key") key: String = ApiKey.themoviedb_acess_key
    ): MovieBody

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = ApiKey.themoviedb_acess_key,
    ): MovieListBody

    //TODO: Receber os dados de configuração e cachear em DataStorage.
}