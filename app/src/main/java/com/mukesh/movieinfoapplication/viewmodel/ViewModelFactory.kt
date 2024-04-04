package com.mukesh.movieinfoapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mukesh.movieinfoapplication.repository.PopularMovieRepository
import com.mukesh.movieinfoapplication.service.ApiHelper

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularMovieViewModel::class.java)) {
            return PopularMovieViewModel(PopularMovieRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}