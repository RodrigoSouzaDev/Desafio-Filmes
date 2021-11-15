package com.example.desafiofilmes.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.desafiofilmes.MainActivityViewModel
import com.example.desafiofilmes.R
import com.example.desafiofilmes.core.extensions.canToastThis
import com.example.desafiofilmes.core.extensions.checkConnection
import com.example.desafiofilmes.core.extensions.expand
import com.example.desafiofilmes.core.extensions.glideThis
import com.example.desafiofilmes.databinding.ActivityMainBinding
import com.example.desafiofilmes.presentation.adapter.SimilarMoviesAdapter
import com.example.desafiofilmes.util.*
import com.google.android.material.appbar.MaterialToolbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: MainActivityViewModel by inject()
    private val adapter by lazy { SimilarMoviesAdapter() }
    private val movieId: Int = MovieIdEnum.COBRA.id
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        setObservers()
        setButtonLikeOnClick()
        if(checkConnection()){
            searchMovie(movieId)
        }
        else{
            canToastThis("Sem Conexão Com a internet")
        }
    }

    private fun setButtonLikeOnClick() {
        if(binding.imgvTbLikes.isGone){
            binding.imgvTbLikes.visibility = View.VISIBLE
        }

        if(!viewModel.movieLike.hasObservers()){
            viewModel.movieLike
                .observe(this,{
                    if(it == false){
                        binding.imgvTbLikes.setImageResource(R.drawable.likes_outlined_icon)
                    }else{
                        binding.imgvTbLikes.setImageResource(R.drawable.likes_filled_icon)
                    }
                })
        }

        binding.imgvTbLikes.setOnClickListener{
            viewModel.setMovieLike()
        }
    }

    private fun searchMovie(movieId: Int) {
        viewModel.getMovieById(movieId)
        viewModel.getSimilarMovies(movieId)
    }

    private fun movieInfoObserver() {
        viewModel.movieBody.observe(this, {
            when (it) {
                MainActivityViewModel.StateMovie.Loading -> {
                    canToastThis("Carregando Informações do Filme")
                }
                is MainActivityViewModel.StateMovie.Error -> {
                    it.error.message?.let { it1 -> canToastThis(it1) }
                }
                is MainActivityViewModel.StateMovie.Sucess -> {
                    binding.tvTbTitle.text = it.movie.movieTitle
                    glideThis(binding.root, it.movie.posterPath, binding.imgviewPoster)
                    binding.includedMovieInfoLayout.textviewGenre.text = it.movie.genres
                    binding.includedMovieInfoLayout.textviewViews.text =
                        it.movie.views
                    binding.includedMovieInfoLayout.textviewReleasedate.text =
                        it.movie.releaseDate
                    binding.includedMovieInfoLayout.textViewLikes.text =
                        it.movie.likes
                    binding.includedMovieInfoLayout.textviewRuntime.text = it.movie.runtime
                    binding.includedMovieInfoLayout.textviewOverview.text = it.movie.overview
                }
            }
        })
    }

    private fun similarMoviesObserver() {

        viewModel.movieList.observe(this, { it ->

            when (it) {
                MainActivityViewModel.StateMovieList.Loading -> {
                    canToastThis("Carregando Filmes Similares")
                }
                is MainActivityViewModel.StateMovieList.Error -> {
                    it.error.message?.let { it1 -> canToastThis(it1) }
                }
                is MainActivityViewModel.StateMovieList.Sucess -> {

                    adapter.submitList(it.movieList)

                    connectAdapter()
                    setButtonOnClick()
                }
            }
        })
    }

    private fun setButtonOnClick() {

        binding
            .includedMovieInfoLayout
            .buttonMovies
            .setOnClickListener {

                binding
                    .includedMovieInfoLayout
                    .recyclerMovies.expand()

                it.visibility = View.GONE
            }
    }

    private fun connectAdapter() {
        binding
            .includedMovieInfoLayout
            .recyclerMovies
            .adapter = adapter
    }

    private fun setObservers() {
        movieInfoObserver()
        similarMoviesObserver()
    }
}