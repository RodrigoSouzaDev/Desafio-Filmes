package com.example.desafiofilmes.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.example.desafiofilmes.presentation.viewmodel.MainActivityViewModel
import com.example.desafiofilmes.R
import com.example.desafiofilmes.core.extensions.canToastThis
import com.example.desafiofilmes.core.extensions.checkConnection
import com.example.desafiofilmes.core.extensions.expand
import com.example.desafiofilmes.core.extensions.retract
import com.example.desafiofilmes.databinding.ActivityMainBinding
import com.example.desafiofilmes.presentation.adapter.SimilarMoviesAdapter
import com.example.desafiofilmes.util.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: MainActivityViewModel by inject()
    private val adapter by lazy { SimilarMoviesAdapter(onMovieClicked()) }
    private val dialog: AlertDialog by lazy {
        createDialogProgress()
    }

    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog.show()
        toolbar = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        setObservers()
        setButtonLikeOnClick()
        setBackButtonOnClick()
        searchMovie(MOVIE_ID)
    }

    private fun searchMovie(id: Int) {

        if(!dialog.isShowing){
            dialog.show()
            configLayoutForNewMovie()
        }

        if(checkConnection()){
            viewModel.getMovieById(id)
            viewModel.getSimilarMovies(id)
        }
        else{

            MaterialAlertDialogBuilder(this@MainActivity)
                .setTitle("No internet connection")
                .setMessage("want to try again?")
                .setNegativeButton("No") { dialog, which ->
                    finish()
                }
                .setPositiveButton("Yes") { dialog, which ->
                    searchMovie(id)
                }.setCancelable(false)
                .create().show()

            canToastThis("You don't have an internet connection,want to try again? ")
        }
    }

    private fun createDialogProgress(): AlertDialog{
        val view = View.inflate(baseContext,R.layout.progress_dialog,null)

        return AlertDialog.Builder(this@MainActivity,R.style.DialogTheme)
            .setView(view)
            .create()
    }

    private fun setBackButtonOnClick() {
        binding.backButton.setOnClickListener{
            onBackPressed()
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

    private fun setObservers() {
        movieInfoObserver()
        similarMoviesObserver()
    }

    private fun movieInfoObserver() {
        viewModel.movieBody.observe(this, {
            when (it) {
                MainActivityViewModel.StateMovie.Loading -> {
                }
                is MainActivityViewModel.StateMovie.Error -> {
                    it.error.message?.let { it1 -> canToastThis(it1) }
                }
                is MainActivityViewModel.StateMovie.Sucess -> {
                    binding.tvTbTitle.text = it.movie.movieTitle
                    glideIt(binding.root, it.movie.posterPath, binding.imgviewPoster)
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

        viewModel.movieList.observe(this, {

            when (it) {
                MainActivityViewModel.StateMovieList.Loading -> {
                }
                is MainActivityViewModel.StateMovieList.Error -> {
                    it.error.message?.let { it1 -> canToastThis(it1) }
                }
                is MainActivityViewModel.StateMovieList.Sucess -> {

                    adapter.submitList(it.movieList)

                    connectAdapter()
                    startButtonsListeners()

                    CoroutineScope(Dispatchers.IO).launch{
                        Thread.sleep(1500)
                        dialog.dismiss()
                    }
                }
            }
        })
    }


    private fun glideIt(with: View, url: String?, into: ImageView){
        if(url != null){
            Glide
                .with(with)
                .load(url)
                .into(into)
        } else {
            Glide
                .with(with)
                .load("@drawable/mock_image_not_found")
                .into(into)
        }

    }
    private fun startButtonsListeners() {

        setButtonLessOnClickListener()
        setButtonMoreOnClickListener()

    }

    private fun configLayoutForNewMovie(){

        binding.appbarLayout.setExpanded(true)
        viewModel.resetMovieLike()
        binding.includedMovieInfoLayout.recyclerMovies.retract()
        binding.scrollView.smoothScrollTo(0,binding.root.top,1000)
        binding.includedMovieInfoLayout.buttonMoviesSeeLess.visibility= View.GONE
        binding.includedMovieInfoLayout.buttonMoviesSeeMore.visibility= View.VISIBLE
    }

    private fun onMovieClicked(): (Int) -> Unit {
        return { searchMovie(it)}
    }

    private fun setButtonMoreOnClickListener() {
        binding
            .includedMovieInfoLayout
            .buttonMoviesSeeMore
            .setOnClickListener {

                binding
                    .includedMovieInfoLayout
                    .recyclerMovies.expand()

                it.visibility = View.GONE
                binding.includedMovieInfoLayout.buttonMoviesSeeLess.visibility= View.VISIBLE
            }
    }

    private fun setButtonLessOnClickListener(){
        binding
            .includedMovieInfoLayout
            .buttonMoviesSeeLess
            .setOnClickListener {

                binding
                    .includedMovieInfoLayout
                    .recyclerMovies.retract()

                it.visibility = View.GONE
                binding.includedMovieInfoLayout.buttonMoviesSeeMore.visibility= View.VISIBLE
            }
    }

    private fun connectAdapter() {
        binding
            .includedMovieInfoLayout
            .recyclerMovies
            .retract()

        binding
            .includedMovieInfoLayout
            .recyclerMovies
            .adapter = adapter
    }

    companion object{
        val MOVIE_ID: Int  = MovieIdEnum.RUBBER.id
    }
}