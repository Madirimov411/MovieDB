package com.uzb7.moviedb.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val isTester = true
    val SERVER_DEVELOPMENT = "https://api.themoviedb.org/3/"
    val SERVER_PRODUCTION = "https://api.themoviedb.org/3/"

    fun baseURL(): String {
        if (isTester) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    private val client= getClient()
    private val retrofit= getRetrofit()

    val apiService: ApiService = retrofit.create(ApiService::class.java)


    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseURL())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val builder=chain.request().newBuilder()
            builder.addHeader(
                "api_key",
                "99b4808386d0dc2136f0e6efe977a911"
            )
            chain.proceed(builder.build())
        })
            .build()

    }


}