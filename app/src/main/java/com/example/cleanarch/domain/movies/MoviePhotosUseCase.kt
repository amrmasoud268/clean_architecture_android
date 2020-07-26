package com.example.cleanarch.domain.movies

import androidx.lifecycle.MutableLiveData
import com.example.cleanarch.core.base.BaseUseCase
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.Photo
import com.example.cleanarch.data.repositories.MoviesPhotosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MoviePhotosUseCase(
    private val moviesPhotosRepository: MoviesPhotosRepository
) :
    BaseUseCase() {

    val moviePhotosLiveData = MutableLiveData<Result<List<Photo>>>()

    suspend fun getMoviePhoto(name: String) = withContext(Dispatchers.IO) {
        moviePhotosLiveData.postValue(Result.Loading)
        try {
            val moviePhotos =
                moviesPhotosRepository.getMoviePhotos(name).photos.photo
            moviePhotosLiveData.postValue(Result.Success(moviePhotos))
        } catch (e: HttpException) {
            moviePhotosLiveData.postValue(Result.Error(e.message()))
        } catch (e: Exception) {
            moviePhotosLiveData.postValue(Result.Error("Some thing want to wrong"))
        }
    }
}