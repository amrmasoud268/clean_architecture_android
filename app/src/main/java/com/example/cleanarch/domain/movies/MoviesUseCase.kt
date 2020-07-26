package com.example.cleanarch.domain.movies

import androidx.lifecycle.MutableLiveData
import com.example.cleanarch.core.base.BaseUseCase
import com.example.cleanarch.data.repositories.MoviesRepository
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MoviesUseCase(private val moviesRepository: MoviesRepository) : BaseUseCase() {

    val moviesLiveData = MutableLiveData<Result<List<Movie>>>()

    suspend fun getMovies() =  withContext(Dispatchers.Default) {
        moviesLiveData.postValue(Result.Loading)
        try {
            moviesLiveData.postValue(Result.Success(moviesRepository.getMovies()))
        } catch (e: Exception) {
            moviesLiveData.postValue(Result.Error("Some thing want to wrong"))
        }
    }

}