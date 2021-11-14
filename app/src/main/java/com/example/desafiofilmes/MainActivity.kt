package com.example.desafiofilmes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.desafiofilmes.databinding.AppBarLayoutBinding
import com.example.desafiofilmes.presentation.adapter.SimilarMoviesAdapter
import com.example.desafiofilmes.util.MovieIdEnum
import com.example.desafiofilmes.util.canToastThis
import com.example.desafiofilmes.util.glideThis
import com.google.android.material.appbar.MaterialToolbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val appbarBinding: AppBarLayoutBinding by lazy {
        AppBarLayoutBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: MainActivityViewModel by inject()
    private val adapter by lazy { SimilarMoviesAdapter() }
    private val movieId: Int = MovieIdEnum.YOUR_NAME.id
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = appbarBinding.toolbar
        toolbar.title = ""
        setContentView(appbarBinding.root)
        setSupportActionBar(toolbar)
        setObservers()
        searchMovie(movieId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.movie_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId ) {
             R.id.menu_likes -> {

                 if (viewModel.movieLike.hasObservers().not()){
                     viewModel.movieLike.observe(this,{
                         if(it == false){
                             item.setIcon(R.drawable.likes_outlined_icon)
                         }else{
                             item.setIcon(R.drawable.likes_filled_icon)
                         }
                     })
                 }

                 item.setOnMenuItemClickListener {
                    viewModel.setMovieLike()
                 }
             }
             else -> {
                 return false
             }
        }
        return true
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
                    toolbar.title = it.movie.movieTitle
                    glideThis(appbarBinding.root, it.movie.posterPath, appbarBinding.imgviewPoster)
                    appbarBinding.includedMovieInfoLayout.textviewGenre.text = it.movie.genres
                    appbarBinding.includedMovieInfoLayout.textviewPopularity.text =
                        it.movie.popularity
                    appbarBinding.includedMovieInfoLayout.textviewReleasedate.text =
                        it.movie.releaseDate
                    appbarBinding.includedMovieInfoLayout.textViewLikes.text =
                        it.movie.voteCount.toString()
                    appbarBinding.includedMovieInfoLayout.textviewRuntime.text = it.movie.runtime
                    appbarBinding.includedMovieInfoLayout.textviewOverview.text = it.movie.overview
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

                    appbarBinding
                        .includedMovieInfoLayout
                        .includedSimilarmoviesList
                        .recylerSimilarmovies
                        .adapter = adapter

                    appbarBinding
                        .includedMovieInfoLayout
                        .includedSimilarmoviesList
                        .similarmoviesButton
                        .setOnClickListener {

                            val layoutParams = appbarBinding
                                .includedMovieInfoLayout
                                .includedSimilarmoviesList
                                .recylerSimilarmovies
                                .layoutParams

                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                            appbarBinding
                                .includedMovieInfoLayout
                                .includedSimilarmoviesList
                                .recylerSimilarmovies
                                .layoutParams = layoutParams

                            it.visibility= View.GONE

                    }
                }
            }
        })
    }

    private fun setObservers() {
        movieInfoObserver()
        similarMoviesObserver()
    }
}