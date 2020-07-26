package com.example.cleanarch.data.store

import com.example.cleanarch.data.apis.MoviePhotosAPI

class MoviesPhotosStoreImpl(private val moviePhotosAPI: MoviePhotosAPI) : MoviesPhotosStore {
    override suspend fun getMoviePhotos(
        name: String
    ) = moviePhotosAPI.getMoviePhotos(name)
}