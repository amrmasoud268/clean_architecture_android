package com.example.cleanarch.ui.view_models

import androidx.lifecycle.viewModelScope
import com.example.cleanarch.core.base.BaseViewModel
import com.example.cleanarch.domain.movies.MoviesSearchUseCase
import kotlinx.coroutines.launch

class SearchMoviesViewModel(private val moviesSearchUseCase: MoviesSearchUseCase) :
    BaseViewModel() {

    val moviesMapLiveData = moviesSearchUseCase.moviesMapLiveData

    fun searchMovieByName(name: String) {
        viewModelScope.launch {
            moviesSearchUseCase.searchAndFilterMovies(name)
        }
    }

}