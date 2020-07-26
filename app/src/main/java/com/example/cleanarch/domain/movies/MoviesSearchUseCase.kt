package com.example.cleanarch.domain.movies

import androidx.lifecycle.MutableLiveData
import com.example.cleanarch.core.base.BaseUseCase
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.Movie
import com.example.cleanarch.data.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesSearchUseCase(
    private val moviesRepository: MoviesRepository
) : BaseUseCase() {

    val moviesMapLiveData = MutableLiveData<Result<Map<Int, List<Movie>>>>()

    suspend fun searchAndFilterMovies(name: String) =
        withContext(Dispatchers.Default) {
            moviesMapLiveData.postValue(Result.Loading)
            try {
                val movies = moviesRepository.getMovies(name)
                val movieMap = mutableMapOf<Int, List<Movie>>()
                if (movies.isNotEmpty())
                    movies.forEach { movie ->
                        movieMap[movie.year]?.toMutableList()?.let {
                            it.add(movie)
                            movieMap[movie.year] = it
                        } ?: kotlin.run {
                            val list = mutableListOf<Movie>()
                            list.add(movie)
                            movieMap[movie.year] = list
                        }
                    }
                if (movieMap.isNotEmpty()) {
                    for ((k, list) in movieMap) {
                        movieMap[k] = list.sortedBy { it.rating }.reversed().takeLast(5)
                    }

                }
                moviesMapLiveData.postValue(Result.Success(movieMap))
            } catch (e: Exception) {
                moviesMapLiveData.postValue(Result.Error("No Data Found"))
            }
        }

}