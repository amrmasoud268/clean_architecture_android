package com.example.cleanarch.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.cleanarch.R
import com.example.cleanarch.core.base.BaseFragment
import com.example.cleanarch.data.Result
import com.example.cleanarch.ui.adapters.MoviesAdapter
import com.example.cleanarch.ui.view_models.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment() {

    private var twoPane = false

    private lateinit var moviesAdapter: MoviesAdapter
    private val movieViewModel: MovieViewModel by viewModel()

    override fun getLayout(): Int = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            twoPane = it.getBoolean(TWO_PANE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        initViewModel()
    }

    private fun setupViews() {
        activity?.let {
            moviesAdapter = MoviesAdapter(it, twoPane = twoPane)

            with(rvMovies) {
                adapter = moviesAdapter
            }
        }
    }

    private fun initViewModel() {
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Success -> {
                    moviesAdapter.updateMoviesAdapter(it.data)
                    hideLoading()
                }
                is Result.Error -> {
                    showError(it.exception)
                    hideLoading()
                }
            }
        })

        movieViewModel.getMovies()
    }

    companion object {
        const val TWO_PANE = "TWO_PANE"
        fun newInstance(twoPlane: Boolean): MoviesFragment {
            return MoviesFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(TWO_PANE, twoPane)
                }
            }
        }
    }
}