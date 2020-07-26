package com.example.cleanarch.ui.view_models

import androidx.lifecycle.viewModelScope
import com.example.cleanarch.core.base.BaseViewModel
import com.example.cleanarch.data.Result
import com.example.cleanarch.domain.movies.MoviesUseCase
import kotlinx.coroutines.launch

class MovieViewModel(private val moviesUseCase: MoviesUseCase) : BaseViewModel() {

    val moviesLiveData = moviesUseCase.moviesLiveData

    fun getMovies() {
        moviesLiveData.value = Result.Loading
        viewModelScope.launch {
            moviesUseCase.getMovies()
        }
    }

}