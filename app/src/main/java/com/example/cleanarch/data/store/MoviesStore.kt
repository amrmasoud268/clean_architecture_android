package com.example.cleanarch.data.store

import com.example.cleanarch.data.model.Movie

interface MoviesStore {
    suspend fun getMovies(): List<Movie>
}