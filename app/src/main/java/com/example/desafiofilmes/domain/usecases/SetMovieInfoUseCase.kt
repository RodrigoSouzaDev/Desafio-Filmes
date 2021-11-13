package com.example.desafiofilmes.domain.usecases

import com.bumptech.glide.Glide
import com.example.desafiofilmes.databinding.AppBarLayoutBinding
import com.example.desafiofilmes.databinding.MovieInfoLayoutBinding
import com.example.desafiofilmes.data.model.Genre
import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.util.ImageBaseUrlEnum
import com.example.desafiofilmes.util.PosterSizeEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetMovieInfoUseCase {

    suspend operator fun invoke(movieBody: MovieBody, appBarLayoutBinding: AppBarLayoutBinding, movieInfoBinding: MovieInfoLayoutBinding){

        val runtime: String = checkRuntime(movieBody.runtime)
        val overview: String = checkOverview(movieBody.overview)
        val genre: String = configGenre(movieBody.genres)

        checkPosterPath(movieBody.posterPath,appBarLayoutBinding)
        setAppBarLayout(movieBody,appBarLayoutBinding)
        setMovieInfoLayout(runtime,overview,genre,movieBody,movieInfoBinding)
    }


    private suspend fun setMovieInfoLayout(
        runtime: String,
        overview: String,
        genre: String,
        movieBody: MovieBody,
        movieInfoBinding: MovieInfoLayoutBinding
    ) {
        withContext(Dispatchers.Main){
            movieInfoBinding.textViewLikes.text = movieBody.voteCount.toString()
            movieInfoBinding.textviewPopularity.text = movieBody.popularity.toString()
            movieInfoBinding.textviewReleasedate.text = movieBody.releaseDate
            movieInfoBinding.textviewRuntime.text = runtime
            movieInfoBinding.textviewOverview.text = overview
            movieInfoBinding.textviewGenre.text = genre
        }
    }

    private suspend fun setAppBarLayout(movieBody: MovieBody, appBarLayoutBinding: AppBarLayoutBinding) {
        withContext(Dispatchers.Main){
            appBarLayoutBinding.toolbar.title = movieBody.movieTitle
        }
    }

    private fun checkOverview(overview: String?): String {
        return if (overview !== null){
            overview
        }else{
            "This movie doesn't have a overview."
        }
    }

    private suspend fun checkPosterPath(posterPath: String?, appBarLayoutBinding: AppBarLayoutBinding ) {
        if (posterPath !== null){
            val url = "${ImageBaseUrlEnum.SECURE_BASE_URL.path}${PosterSizeEnum.POSTER_SIZE_W500.size}${posterPath}"
            withContext(Dispatchers.Main){
                Glide.with(appBarLayoutBinding.root)
                    .load(url)
                    .into(appBarLayoutBinding.imgviewPoster)
            }
        }
        else{
            withContext(Dispatchers.Main){
                Glide.with(appBarLayoutBinding.root)
                    .load("@drawable/mock_image_not_found")
                    .into(appBarLayoutBinding.imgviewPoster)
            }
        }
    }

    private fun checkRuntime(runtime: Int?): String{
        return if(runtime !== null) {
            configRuntime(runtime)
        } else {
            " - "
        }
    }

    private fun configRuntime(runtime: Int?):String
    {
        return "$runtime minutes"
    }

    private fun configGenre(genres: List<Genre>):String
    {
        var genreString: String = ""

        for (genre in genres){
            genreString += if(genre == genres.last()){
                genre.genreName
            } else{
                "${genre.genreName}, "
            }
        }
        return genreString
    }
}