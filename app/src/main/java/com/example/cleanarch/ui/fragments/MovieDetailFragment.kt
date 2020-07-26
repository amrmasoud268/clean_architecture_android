package com.example.cleanarch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.cleanarch.R
import com.example.cleanarch.core.utils.setList
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.Movie
import com.example.cleanarch.data.model.Photo
import com.example.cleanarch.ui.adapters.PhotosAdapter
import com.example.cleanarch.ui.customs.CustomLinearSnapHelper
import com.example.cleanarch.ui.view_models.MoviePhotoViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private val moviePhotoViewModel: MoviePhotoViewModel by viewModel()
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(MOVIE_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

        initMoviePhotoObserver()
    }

    private fun setupViews() {
        movie?.let { movie ->
            moviePhotoViewModel.getMoviePhotos(movie.title)
            activity?.let { it.title = movie.title }
            tvYear.text = movie.year.toString()
            tvTitle.text = movie.title
            activity?.let {
                castChip.setList(it, R.layout.item_chip, movie.cast)
                genresChip.setList(it, R.layout.item_chip, movie.genres)
            }
        }
    }

    private fun initMoviePhotoObserver() {
        moviePhotoViewModel.moviePhotosLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                //is Result.Loading ->
                is Result.Success -> showPhotos(it.data)
                is Result.Error -> showError()
            }
        })
    }

    private fun showError() {

    }

    private fun showPhotos(photos: List<Photo>) {
        CustomLinearSnapHelper().attachToRecyclerView(rvPhotos)
        rvPhotos?.adapter = PhotosAdapter(if (photos.size > 2) photos.subList(0, 2) else photos)
    }

    companion object {
        const val MOVIE_ITEM = "movie_item"
    }
}