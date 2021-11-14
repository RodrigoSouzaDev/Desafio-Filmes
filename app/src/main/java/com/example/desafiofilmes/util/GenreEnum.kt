package com.example.desafiofilmes.util


enum class GenreEnum (val id:Int){
    ACTION(28){ override fun toString(): String {return "Action"}},
    ADVENTURE(12){ override fun toString(): String {return "Adventure"}},
    ANIMATION(16){ override fun toString(): String {return "Animation"}},
    COMEDY(35){ override fun toString(): String {return "Comedy"}},
    CRIME(80){ override fun toString(): String {return "Crime"}},
    DOCUMENTARY(99){ override fun toString(): String {return "Documentary"}},
    DRAMA(18){ override fun toString(): String {return "Drama"}},
    FAMILY(10751){ override fun toString(): String {return "Family"}},
    FANTASY(14){ override fun toString(): String {return "Fantasy"}},
    HISTORY(36){ override fun toString(): String {return "History"}},
    HORROR(27){ override fun toString(): String {return "Horror"}},
    MUSIC(10402){ override fun toString(): String {return "Music"}},
    MYSTERY(9648){ override fun toString(): String {return "Mystery"}},
    ROMANCE(10749){ override fun toString(): String {return "Romance"}},
    SCIENCE_FICTION(878){ override fun toString(): String {return "Science Fiction"}},
    TV_MOVIE(10770){ override fun toString(): String {return "TV Movie"}},
    THRILLER(53){ override fun toString(): String {return "Thriller"}},
    WAR(10752){ override fun toString(): String {return "War"}},
    WESTERN(37){ override fun toString(): String {return "Western"}},
}

fun main () {
    val genres = GenreEnum.values()

    for (enum in genres){
        if(enum.id == 28){
            println(enum.toString())
        }
    }
}
