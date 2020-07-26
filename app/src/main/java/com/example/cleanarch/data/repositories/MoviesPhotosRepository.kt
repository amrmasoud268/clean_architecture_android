package com.example.cleanarch.data.repositories

import com.example.cleanarch.data.store.MoviesPhotosStore

class MoviesPhotosRepository(private val moviesPhotosStore: MoviesPhotosStore) {
    suspend fun getMoviePhotos(
        name: String
    ) = moviesPhotosStore.getMoviePhotos(name)
}