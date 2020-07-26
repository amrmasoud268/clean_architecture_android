package com.example.cleanarch.ui.activities

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.cleanarch.R
import com.example.cleanarch.core.base.BaseActivity
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.Movie
import com.example.cleanarch.ui.adapters.SearchMoviesAdapter
import com.example.cleanarch.ui.view_models.SearchMoviesViewModel
import kotlinx.android.synthetic.main.activity_search_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMoviesActivity : BaseActivity() {

    private val searchMoviesViewModel: SearchMoviesViewModel by viewModel()

    private lateinit var searchMoviesAdapter: SearchMoviesAdapter

    override fun getLayout(): Int = R.layout.activity_search_movies

    override fun init() {
        setupSearchView()
        initObserver()
    }

    private fun setupSearchView() {
        searchMoviesAdapter = SearchMoviesAdapter(this)

        rvSearchedMovies.adapter = searchMoviesAdapter

        moviesSearchView.isActivated = true
        moviesSearchView.queryHint = "Type your keyword here"
        moviesSearchView.onActionViewExpanded()
        moviesSearchView.isIconified = false
        moviesSearchView.clearFocus()

        moviesSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { searchMoviesViewModel.searchMovieByName(it) }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.e("test", p0)
                return false
            }

        })
    }

    private fun initObserver() {
        searchMoviesViewModel.moviesMapLiveData.observe(this, Observer { it ->
            when (it) {
                //is Result.Loading -> showLoading()
                is Result.Success -> searchMoviesAdapter.updateMap(it.data as MutableMap<Int, List<Movie>>)
                is Result.Error -> showError(it.exception)
            }
        })
    }

}