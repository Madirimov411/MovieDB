package com.uzb7.moviedb.data.remote

import com.uzb7.moviedb.model.Popular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("popular")
    fun getPopular(@Query("api_key")api_key:String="99b4808386d0dc2136f0e6efe977a911"):Call<Popular>



}