package com.mukesh.movieinfoapplication.service

import com.mukesh.movieinfoapplication.model.MovieDbResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String?,
        @Query("page") page: Int
    ): MovieDbResponse

}