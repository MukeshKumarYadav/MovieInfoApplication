package com.mukesh.movieinfoapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.movieinfoapplication.R
import com.mukesh.movieinfoapplication.databinding.MoviePopularListBinding
import com.mukesh.movieinfoapplication.listener.MovieListener
import com.mukesh.movieinfoapplication.model.Movie
import java.util.*

class PopularMovieAdapter(private var movies: ArrayList<Movie>, private var context: Context) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieHolder>(),
    Filterable {
    private val moviesListFull: ArrayList<Movie> = ArrayList<Movie>(movies)
    private val movieListener: MovieListener = context as MovieListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieHolder {
        val moviePopularListBinding: MoviePopularListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.movie_popular_list,
            parent,
            false
        )
        return PopularMovieHolder(moviePopularListBinding)
    }

    override fun onBindViewHolder(holder: PopularMovieHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.movieData(movie, movieListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class PopularMovieHolder(moviePopularListBinding: MoviePopularListBinding) :
        RecyclerView.ViewHolder(moviePopularListBinding.root) {
        private val binding: MoviePopularListBinding = moviePopularListBinding
        fun movieData(movie: Movie, listener: MovieListener) {
            binding.movie = movie
            binding.root
                .setOnClickListener { listener.onMovieClick(movie) }
        }

    }

    fun loadMovieList(movieList: List<Movie>) {
        this.movies.apply {
            addAll(movieList)
        }
        this.moviesListFull.apply {
            addAll(movieList)
        }
    }

    override fun getFilter(): Filter {
        return movieFilter
    }


    private val movieFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredMovieList: ArrayList<Movie> = ArrayList<Movie>()
            if (constraint.isEmpty()) {
                filteredMovieList.addAll(moviesListFull)
            } else {
                val movieFilterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in moviesListFull) {
                    if (item.getTitle()?.lowercase(Locale.getDefault())
                            ?.contains(movieFilterPattern) == true
                    ) {
                        filteredMovieList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredMovieList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            movies.clear()
            movies.addAll((results.values as List<Movie>))
            notifyDataSetChanged()
        }
    }

}