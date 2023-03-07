package com.uzb7.moviedb.model.review

data class Review(
    val id: Int,
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)