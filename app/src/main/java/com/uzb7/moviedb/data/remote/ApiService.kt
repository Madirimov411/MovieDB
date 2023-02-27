package com.uzb7.moviedb.data.remote

import com.uzb7.moviedb.model.Popular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("popular")
    fun getPopular(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<Popular>

    @GET("top_rated")
    fun getTopRated(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<Popular>

    @GET("upcoming")
    fun getUpcoming(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<Popular>


}