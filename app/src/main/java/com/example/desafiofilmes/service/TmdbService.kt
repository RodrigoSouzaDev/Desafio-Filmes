package com.example.desafiofilmes.service

import com.example.desafiofilmes.model.Movie
import com.example.desafiofilmes.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: Int,
        @Query("api_key") key: String = ApiKey.themoviedb_acess_key
    ): Movie

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = ApiKey.themoviedb_acess_key,
    ): MovieList

    //TODO: Receber os dados de configuração e cachear em DataStorage.
}

//
//@GET("configuration")
//suspend fun getConfiguration(
//    @Query("api_key") key: String = ApiKey.themoviedb_acess_key
//)