package com.example.cleanarch.data.store

import com.example.cleanarch.R
import com.example.cleanarch.core.utils.JsonResourceReader
import com.example.cleanarch.data.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesStoreImpl(private val jsonResourceReader: JsonResourceReader) : MoviesStore {
    override suspend fun getMovies() = withContext(Dispatchers.Default) {
        val moviesResponse =
            jsonResourceReader.constructUsingGson(MoviesResponse::class.java, R.raw.movies)
        moviesResponse.movies
    }
}