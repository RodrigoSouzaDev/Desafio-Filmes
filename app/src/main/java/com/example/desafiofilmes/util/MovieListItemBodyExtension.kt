package com.example.desafiofilmes.util

import com.example.desafiofilmes.data.model.MovieListItemBody
import com.example.desafiofilmes.domain.model.MovieListItem

fun MovieListItemBody.toModel(): MovieListItem{
    return MovieListItem(
        this.id,
        this.title,
        configGenre(this.genres),
        checkReleaseDate(this.releaseDate),
        this.overview,
        configPosterPath(this.posterPath, PosterSizeEnum.POSTER_SIZE_W185.size)
    )
}

private fun checkReleaseDate(releaseDate: String?): String{
    return if(releaseDate !== null) {
        releaseDate
    } else {
        " - "
    }
}


private fun configGenre(genresID: List<Int>):String
{
    var genreString: String = ""
    var genreList: ArrayList<String> = ArrayList()

    for (id in genresID){
        for (enum in GenreEnum.values()){
            if (id == enum.id){
                genreList.add(enum.toString())
                break
            }
        }
    }

    for (genre in genreList){
        genreString += if(genre == genreList.last()){
            genre
        } else{
            "${genre}, "
        }
    }
    return genreString
}

private fun configPosterPath(posterPath: String?, posterSize:String): String? {
    return if (posterPath != null){
        "${ImageBaseUrlEnum.SECURE_BASE_URL.path}${posterSize}${posterPath}"
    } else {
        null
    }
}