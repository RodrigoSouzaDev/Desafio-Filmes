package com.example.desafiofilmes.util

import com.example.desafiofilmes.data.model.Genre
import com.example.desafiofilmes.data.model.MovieBody
import com.example.desafiofilmes.domain.model.Movie


fun MovieBody.toModel(): Movie {
    return Movie(
        this.movieId,
        this.movieTitle,
        configGenre(this.genres),
        this.popularity.toString(),
        this.releaseDate,
        this.voteCount,
        checkRuntime(this.runtime),
        configPosterPath(this.posterPath),
        checkOverview(this.overview),
        checkTagline(this.tagline)
    )
}

private fun checkTagline(tagline: String?): String {
    return if (tagline !== null){
        tagline
    }else{
        ""
    }
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

private fun configPosterPath(posterPath: String?): String? {
    return if (posterPath != null){
        "${ImageBaseUrlEnum.SECURE_BASE_URL.path}${PosterSizeEnum.POSTER_SIZE_W500.size}${posterPath}"
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