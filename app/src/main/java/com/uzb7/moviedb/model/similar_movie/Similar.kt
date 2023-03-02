package com.uzb7.moviedb.model.similar_movie

data class Similar(
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)