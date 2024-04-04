package com.mukesh.movieinfoapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.mukesh.movieinfoapplication.R
import com.mukesh.movieinfoapplication.databinding.ActivityMovieDetailBinding
import com.mukesh.movieinfoapplication.model.Movie

/**
 * [MovieDetailActivity]
 * Screen to show the details of the clicked/selected movie in HomeScreen
 * @author Mukesh Kumar Yadav on 2024-04-04
 */
class MovieDetailActivity : AppCompatActivity() {
    private var movie: Movie? = null
    private var activityMovieDetailBinding: ActivityMovieDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        activityMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent: Intent = intent
        movie = intent.getParcelableExtra("movie_tag")
        setValue()
    }

    //set the values
    private fun setValue() {
        title = movie!!.getOriginalTitle()
        activityMovieDetailBinding?.movie = movie
    }

    //Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}