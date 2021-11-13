package com.example.desafiofilmes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.desafiofilmes.databinding.*
import com.example.desafiofilmes.util.MovieIdEnum
import com.example.desafiofilmes.util.glideThis
import com.google.android.material.appbar.MaterialToolbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val appbarBinding: AppBarLayoutBinding by lazy { AppBarLayoutBinding.inflate(layoutInflater)}
    private val viewModel: MainActivityViewModel by inject()
    private val movieId: Int = MovieIdEnum.KILL_BILL_VOLUME_1.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setObservers()
        setSupportActionBar(appbarBinding.toolbar)
        searchMovie(movieId)
        setContentView(appbarBinding.root)
    }

    private fun searchMovie(movieId: Int){
        viewModel.getMovieById(movieId)
        viewModel.getSimilarMovies(movieId)
    }

    private fun setObservers(){
        viewModel.movieBody.observe(this,{
            glideThis(appbarBinding.root,it.posterPath,appbarBinding.imgviewPoster)
            //TODO : MovieTitle
            supportActionBar?.title = it.movieTitle
            appbarBinding.includedMovieInfoLayout.textviewGenre.text = it.genres
            appbarBinding.includedMovieInfoLayout.textviewPopularity.text = it.popularity
            appbarBinding.includedMovieInfoLayout.textviewReleasedate.text = it.releaseDate
            appbarBinding.includedMovieInfoLayout.textViewLikes.text = it.voteCount.toString()
            appbarBinding.includedMovieInfoLayout.textviewRuntime.text = it.runtime
            appbarBinding.includedMovieInfoLayout.textviewOverview.text = it.overview
        })
        viewModel.movieList.observe(this,{


        })
    }


}