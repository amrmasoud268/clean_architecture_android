package com.example.cleanarch.data.store

import com.example.cleanarch.data.model.MoviePhotosResponse

interface MoviesPhotosStore {
    suspend fun getMoviePhotos(name: String): MoviePhotosResponse
}