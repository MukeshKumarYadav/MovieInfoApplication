package com.mukesh.movieinfoapplication.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.movieinfoapplication.R
import com.mukesh.movieinfoapplication.adapter.HighRatingMovieAdapter
import com.mukesh.movieinfoapplication.adapter.PopularMovieAdapter
import com.mukesh.movieinfoapplication.databinding.ActivityMainBinding
import com.mukesh.movieinfoapplication.listener.MovieListener
import com.mukesh.movieinfoapplication.model.Movie
import com.mukesh.movieinfoapplication.service.ApiHelper
import com.mukesh.movieinfoapplication.service.RetrofitBuilder
import com.mukesh.movieinfoapplication.utils.Status
import com.mukesh.movieinfoapplication.utils.Util
import com.mukesh.movieinfoapplication.viewmodel.PopularMovieViewModel
import com.mukesh.movieinfoapplication.viewmodel.ViewModelFactory

/**
 * [MainActivity]
 * Home screen to show the list of high rated movies and list of movies  in grid form
 * @author Mukesh Kumar Yadav on 2024-04-04
 */
class MainActivity : AppCompatActivity(), MovieListener {
    private var popularMovieAdapter: PopularMovieAdapter? = null
    private var highRatingMovieAdapter: HighRatingMovieAdapter? = null
    private lateinit var viewModel: PopularMovieViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private var broadcastReceiver: BroadcastReceiver? = null
    private var page: Int = 0
    private var isPaginationAllowed: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(PopularMovieViewModel::class.java)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Popular Movie"
        activityMainBinding.nestedScrollView
        checkNetwork()
        setUpPagination()
    }

    /**
     * setup pagination
     */
    private fun setUpPagination() {
        activityMainBinding.nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if ((scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight) && isPaginationAllowed)) {
                    activityMainBinding.progressBar.visibility = View.VISIBLE
                    isPaginationAllowed = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel.getValue(++page)
                    }, 3000)
                }
            })

    }

    /*
     * handle on click of movie thumbnail
     */
    override fun onMovieClick(movie: Movie) {
        val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
        intent.putExtra("movie_tag", movie)
        startActivity(intent)
    }

    /*
    *Setup data change observers
    */
    private fun setupObservers() {
        viewModel.data.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        activityMainBinding.progressBar.visibility = View.GONE
                        val movieList = resource.data?.movies
                        if (null != movieList) {
                            loadMovieLists(movieList)
                        }

                        Util.setVisibility(
                            arrayOf(
                                activityMainBinding.rvPopularMovieVertical,
                                activityMainBinding.rvPopularMovieHorizontal,
                                activityMainBinding.tvHighRatedMovie,
                                activityMainBinding.tvMovieList
                            ), View.VISIBLE
                        )


                    }

                    Status.ERROR -> {
                        Util.setVisibility(
                            arrayOf(
                                activityMainBinding.rvPopularMovieVertical,
                                activityMainBinding.rvPopularMovieHorizontal,
                                activityMainBinding.tvHighRatedMovie,
                                activityMainBinding.tvMovieList,
                                activityMainBinding.progressBar
                            ), View.GONE
                        )
                    }

                    Status.LOADING -> {
                        activityMainBinding.progressBar.visibility = View.VISIBLE
                        if (isPaginationAllowed) {
                            Util.setVisibility(
                                arrayOf(
                                    activityMainBinding.rvPopularMovieVertical,
                                    activityMainBinding.rvPopularMovieHorizontal,
                                    activityMainBinding.tvHighRatedMovie,
                                    activityMainBinding.tvMovieList
                                ), View.GONE
                            )
                        }
                    }
                }
            }
        }

    }

    /*
     *Check Network availability
     */
    private fun checkNetwork() {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    try {
                        getRecyclerView()
                        setupObservers()
                        if (!Util.isOnline(context)) {
                            loadMovieLists(arrayListOf())
                            Util.setVisibility(
                                arrayOf(
                                    activityMainBinding.rvPopularMovieVertical,
                                    activityMainBinding.rvPopularMovieHorizontal,
                                    activityMainBinding.tvHighRatedMovie,
                                    activityMainBinding.tvMovieList,
                                    activityMainBinding.progressBar
                                ), View.GONE
                            )
                            activityMainBinding.tvNoInternet.visibility = View.VISIBLE

                        } else {
                            page = 1
                            viewModel.getValue(page)

                            activityMainBinding.tvNoInternet.visibility = View.GONE
                        }

                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }
            }.also {
                broadcastReceiver = it
            }, filter
        )
    }

    /*
    * Load the list of movies
    */
    @SuppressLint("NotifyDataSetChanged")
    private fun loadMovieLists(listOfMovie: List<Movie>) {
        popularMovieAdapter?.apply {
            loadMovieList(listOfMovie)
            notifyDataSetChanged()
        }
        isPaginationAllowed = true


        highRatingMovieAdapter?.apply {
            loadMovieList(listOfMovie)
            notifyDataSetChanged()
        }
    }

    /*
     *Setup RecyclerView
     */
    private fun getRecyclerView() {
        highRatingMovieAdapter = HighRatingMovieAdapter(arrayListOf(), this)
        activityMainBinding.rvPopularMovieHorizontal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityMainBinding.rvPopularMovieHorizontal.itemAnimator = DefaultItemAnimator()
        activityMainBinding.rvPopularMovieHorizontal.adapter = highRatingMovieAdapter
        popularMovieAdapter = PopularMovieAdapter(arrayListOf(), this)
        activityMainBinding.rvPopularMovieVertical.layoutManager = GridLayoutManager(this, 3)
        activityMainBinding.rvPopularMovieVertical.itemAnimator = DefaultItemAnimator()
        activityMainBinding.rvPopularMovieVertical.adapter = popularMovieAdapter
    }

    /*
    *show search option in the menu bar
    */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie_menu_bar, menu)
        val menuItem = menu.findItem(R.id.movie_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                popularMovieAdapter!!.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
