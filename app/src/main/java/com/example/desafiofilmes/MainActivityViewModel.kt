package com.example.desafiofilmes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiofilmes.databinding.AppBarLayoutBinding
import com.example.desafiofilmes.databinding.MovieInfoLayoutBinding
import com.example.desafiofilmes.domain.usecases.GetMovieByIdUseCase
import com.example.desafiofilmes.domain.usecases.GetSimilarMoviesUseCase
import com.example.desafiofilmes.domain.usecases.SetMovieInfoUseCase
import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.data.model.MovieList
import com.example.desafiofilmes.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivityViewModel(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
    ): ViewModel() {

        private val setMovieInfoUseCase = SetMovieInfoUseCase()

        private val _movie = MutableLiveData<Movie>()
        private val _movieList = MutableLiveData<MovieList>()

        val movieBody: LiveData<Movie> = _movie
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

    fun setMovieInfo(movieBody: MovieBody, appbarLayoutBinding: AppBarLayoutBinding, movieInfoLayoutBinding: MovieInfoLayoutBinding){
        viewModelScope.launch(Dispatchers.IO){
            setMovieInfoUseCase.invoke(movieBody,appbarLayoutBinding,movieInfoLayoutBinding)
        }
    }
}