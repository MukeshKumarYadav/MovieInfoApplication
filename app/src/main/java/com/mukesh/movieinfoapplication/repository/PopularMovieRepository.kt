package com.mukesh.movieinfoapplication.repository

import com.mukesh.movieinfoapplication.service.ApiHelper

class PopularMovieRepository(private val apiHelper: ApiHelper) {
    suspend fun getPopularMovie(key: String, page: Int) = apiHelper.getPopularMovie(key, page)
}