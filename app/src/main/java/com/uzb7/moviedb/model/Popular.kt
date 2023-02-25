package com.uzb7.moviedb.model

data class Popular(
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)