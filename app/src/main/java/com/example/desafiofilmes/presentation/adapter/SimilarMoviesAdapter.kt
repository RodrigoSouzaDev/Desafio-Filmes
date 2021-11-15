package com.example.desafiofilmes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiofilmes.databinding.MovieItemBinding
import com.example.desafiofilmes.domain.model.MovieListItem

class SimilarMoviesAdapter(val onMovieClicked:(Int)-> Unit ): ListAdapter<MovieListItem, SimilarMoviesAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieListItem) {
            binding.movieitemTextviewMovietitle.text = item.title
            binding.movieitemTextviewOverview.text = item.overview
            binding.movieitemTextviewGenres.text = item.genres
            binding.movieitemRoot.setOnClickListener{ onMovieClicked(item.id) }

            Glide
                .with(binding.root.context)
                .load(item.posterPath)
                .into(binding.movieitemImgviewPoster)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem) = oldItem == newItem
    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem) = oldItem.id == newItem.id
}