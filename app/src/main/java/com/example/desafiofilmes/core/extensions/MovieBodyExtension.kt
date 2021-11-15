package com.example.desafiofilmes.core.extensions

import com.example.desafiofilmes.data.model.Genre
import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.domain.model.Movie
import com.example.desafiofilmes.domain.model.MovieListItem
import com.example.desafiofilmes.util.ImageBaseUrlEnum
import com.example.desafiofilmes.util.PosterSizeEnum


fun MovieBody.toModel(): Movie {
    return Movie(
        this.movieId,
        this.movieTitle,
        configGenre(this.genres),
        configViews(this.views),
        this.releaseDate,
        configLikes(this.likes),
        checkRuntime(this.runtime),
        configPosterPath(this.posterPath, PosterSizeEnum.POSTER_SIZE_W500.size),
        checkOverview(this.overview),
    )
}

private fun configViews(views: Double):String{
    var viewsString= format(views,1)
    viewsString += "k Views"
    return viewsString
}

private fun configLikes(likes: Int):String{
    var likeString = format((likes.toDouble())/1000,1)
    likeString += "k Likes"
    return likeString
}

private fun format(num:Double, digits: Int):String{
    return "%.${digits}f".format(num)
}


private fun checkOverview(overview: String?): String {
    return if (overview !== null){
        overview
    }else{
        "This movie doesn't have a overview."
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

private fun configPosterPath(posterPath: String?, posterSize:String): String? {
    return if (posterPath != null){
        "${ImageBaseUrlEnum.SECURE_BASE_URL.path}${posterSize}${posterPath}"
    } else {
        null
    }
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