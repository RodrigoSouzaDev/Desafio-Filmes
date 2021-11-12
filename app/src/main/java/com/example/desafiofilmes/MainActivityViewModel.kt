package com.example.desafiofilmes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiofilmes.domain.usecases.GetMovieByIdUseCase
import com.example.desafiofilmes.domain.usecases.GetSimilarMoviesUseCase
import com.example.desafiofilmes.model.Movie
import com.example.desafiofilmes.model.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivityViewModel(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
    ): ViewModel() {

        private val _movie = MutableLiveData<Movie>()
        private val _movieList = MutableLiveData<MovieList>()

        val movie: LiveData<Movie> = _movie
        val movieList: LiveData<MovieList> = _movieList

        fun getMovieById(movieId: Int){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _movie.postValue(getMovieByIdUseCase.invoke(movieId))
                } catch (e: Exception){
                    println(" <<< Erro : ${e.message} >>>")
                }
            }
        }

        fun getSimilarMovies(movieId: Int){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                   _movieList.postValue(getSimilarMoviesUseCase.invoke(movieId))
                } catch (e: Exception){
                    println(" <<< Erro : ${e.message} >>>")
            }
        }
    }
}