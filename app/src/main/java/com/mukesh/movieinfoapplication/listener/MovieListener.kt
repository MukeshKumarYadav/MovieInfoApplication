package com.mukesh.movieinfoapplication.listener

import com.mukesh.movieinfoapplication.model.Movie


interface MovieListener {
    fun onMovieClick(movie: Movie)
}