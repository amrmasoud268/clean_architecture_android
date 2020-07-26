package com.example.cleanarch.data.repositories

import com.example.cleanarch.core.base.BaseRepository
import com.example.cleanarch.data.store.MoviesStore

class MoviesRepository(private val moviesStore: MoviesStore) : BaseRepository(){

    suspend fun getMovies() = moviesStore.getMovies()

    suspend fun getMovies(name: String) =
        moviesStore.getMovies().filter { it.title.contains(name) }
}