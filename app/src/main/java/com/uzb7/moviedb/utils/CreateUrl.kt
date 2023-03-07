package com.uzb7.moviedb.utils

object CreateUrl {

    fun youtubeImage(id:String):String{
        return "https://img.youtube.com/vi/${id}/0.jpg"
    }

    fun imageOpen(path:String):String{
        return "https://image.tmdb.org/t/p/w500${path}"
    }

    fun youtubeOpen(path: String):String{
        return "https://www.youtube.com/watch?v=${path}"
    }

}