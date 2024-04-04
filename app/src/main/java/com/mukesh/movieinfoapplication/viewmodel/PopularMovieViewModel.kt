package com.mukesh.movieinfoapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mukesh.movieinfoapplication.repository.PopularMovieRepository
import com.mukesh.movieinfoapplication.utils.Constant
import com.mukesh.movieinfoapplication.utils.Resource
import kotlinx.coroutines.Dispatchers

class PopularMovieViewModel(private val popularMovieRepository: PopularMovieRepository) :
    ViewModel() {
    private val key: String = Constant.key
    var result = MutableLiveData<Int>()


    fun getValue(page: Int) {
        result.postValue(page)
    }

    var data = result.switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = popularMovieRepository.getPopularMovie(key, it)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

}