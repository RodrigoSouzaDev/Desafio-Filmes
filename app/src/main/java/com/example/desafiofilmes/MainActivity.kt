package com.example.desafiofilmes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofilmes.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnclick()
        setObservers()
        setContentView(binding.root)
    }

    private fun setOnclick(){
        binding.btProcurar.setOnClickListener {
            //viewModel.getMovieById(binding.etIdField.text.toString().toInt())
            viewModel.getSimilarMovies(binding.etIdField.text.toString().toInt())
        }
    }

    private fun setObservers(){
//        viewModel.movie.observe(this,{
//            binding.tvInfo.text = it.toString()
//        })
        viewModel.movieList.observe(this,{
            binding.tvInfo.text = it.toString()
        })
    }
}