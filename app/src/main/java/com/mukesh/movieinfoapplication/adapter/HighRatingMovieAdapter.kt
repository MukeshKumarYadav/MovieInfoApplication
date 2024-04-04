package com.mukesh.movieinfoapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.movieinfoapplication.R
import com.mukesh.movieinfoapplication.databinding.HighRatedMovieListBinding
import com.mukesh.movieinfoapplication.listener.MovieListener
import com.mukesh.movieinfoapplication.model.Movie

class HighRatingMovieAdapter(private var movies: ArrayList<Movie>, private var context: Context) :
    RecyclerView.Adapter<HighRatingMovieAdapter.HighRatingMovieHolder>() {
    private val movieListener: MovieListener = context as MovieListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighRatingMovieHolder {
        val highRatedMovieListBinding: HighRatedMovieListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.high_rated_movie_list,
            parent,
            false
        )
        return HighRatingMovieHolder(highRatedMovieListBinding)
    }

    override fun onBindViewHolder(holder: HighRatingMovieHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.movieData(movie, movieListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class HighRatingMovieHolder(highRatedMovieListBinding: HighRatedMovieListBinding) :
        RecyclerView.ViewHolder(highRatedMovieListBinding.root) {
        private val binding: HighRatedMovieListBinding = highRatedMovieListBinding
        fun movieData(movie: Movie, listener: MovieListener) {
            binding.movie = movie
            binding.root
                .setOnClickListener { listener.onMovieClick(movie) }
        }

    }

    fun loadMovieList(movieList: List<Movie>) {
        this.movies.apply {
            clear()
            addAll(movieList)
        }
    }
}