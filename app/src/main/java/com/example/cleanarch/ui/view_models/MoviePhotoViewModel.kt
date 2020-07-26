package com.example.cleanarch.ui.view_models

import androidx.lifecycle.viewModelScope
import com.example.cleanarch.core.base.BaseViewModel
import com.example.cleanarch.domain.movies.MoviePhotosUseCase
import kotlinx.coroutines.launch

class MoviePhotoViewModel(private val moviePhotosUseCase: MoviePhotosUseCase) : BaseViewModel() {

    val moviePhotosLiveData = moviePhotosUseCase.moviePhotosLiveData

    fun getMoviePhotos(name: String) {
        viewModelScope.launch {
            moviePhotosUseCase.getMoviePhoto(name)
        }
    }
}