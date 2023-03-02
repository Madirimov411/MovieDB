package com.uzb7.moviedb.data.remote

import com.uzb7.moviedb.model.Popular
import com.uzb7.moviedb.model.similar_movie.Similar
import com.uzb7.moviedb.model.youtube_videos.AboutMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    fun getPopular(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "99b4808386d0dc2136f0e6efe977a911"
    ): Call<Popular>

    @GET("top_rated")
    fun getTopRated(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "99b4808386d0dc2136f0e6efe977a911"
    ): Call<Popular>

    @GET("upcoming")
    fun getUpcoming(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "99b4808386d0dc2136f0e6efe977a911"
    ): Call<Popular>

    @GET("now_playing")
    fun getNowPlaying(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "99b4808386d0dc2136f0e6efe977a911"
    ): Call<Popular>

    @GET("{id}")
    fun getMovieById(
        @Path("id") id: Int,
        @Query("api_key") api_key: String = "99b4808386d0dc2136f0e6efe977a911",
        @Query("append_to_response") append_to_response:String = "videos,images"
    ):Call<AboutMovie>

    @GET("{id}/similar")
    fun getSimilarMovie(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "99b4808386d0dc2136f0e6efe977a911"
    ):Call<Similar>




}