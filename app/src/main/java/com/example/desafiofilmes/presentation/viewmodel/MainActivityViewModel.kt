package com.example.desafiofilmes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiofilmes.domain.usecases.GetMovieByIdUseCase
import com.example.desafiofilmes.domain.usecases.GetSimilarMoviesUseCase
import com.example.desafiofilmes.domain.model.Movie
import com.example.desafiofilmes.domain.model.MovieListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
    ): ViewModel() {

        private val _movie = MutableLiveData<StateMovie>()
        private val _movieList = MutableLiveData<StateMovieList>()
        private val _movieLike = MutableLiveData<Boolean>().apply { value = false }

        val movieBody: LiveData<StateMovie> = _movie
        val movieList: LiveData<StateMovieList> = _movieList
        val movieLike: LiveData<Boolean> = _movieLike

        fun getMovieById(movieId: Int){
            viewModelScope.launch (Dispatchers.IO) {
              getMovieByIdUseCase.execute(movieId)
                  .onStart {
                      _movie.postValue(StateMovie.Loading)
                  }
                  .catch {
                      _movie.postValue(StateMovie.Error(it))
                  }
                  .collect {
                      _movie.postValue(StateMovie.Sucess(it))
                  }
            }
        }

        fun getSimilarMovies(movieId: Int){
            viewModelScope.launch (Dispatchers.IO) {
                getSimilarMoviesUseCase.execute(movieId)
                    .onStart {
                        _movieList.postValue(StateMovieList.Loading)
                    }
                    .catch {
                        _movieList.postValue(StateMovieList.Error(it))
                    }
                    .collect {
                        _movieList.postValue(StateMovieList.Sucess(it))
                    }
        }
    }

    fun setMovieLike(){
        if(movieLike.value == false){
            _movieLike.postValue(true)
        }else{
            _movieLike.postValue(false)
        }
    }

    fun resetMovieLike(){
        _movieLike.postValue(false)
    }

    sealed class StateMovie{
        object Loading: StateMovie()
        data class Sucess (val movie: Movie): StateMovie()
        data class Error (val error: Throwable): StateMovie()
    }

    sealed class StateMovieList{
        object Loading: StateMovieList()
        data class Sucess (val movieList: List<MovieListItem>): StateMovieList()
        data class Error (val error: Throwable): StateMovieList()
    }
}