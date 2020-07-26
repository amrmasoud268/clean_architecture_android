package com.example.cleanarch.data.repositories

import com.example.cleanarch.base.JsonReader
import com.example.cleanarch.data.model.MoviePhotosResponse
import com.example.cleanarch.data.store.MoviesPhotosStore
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class MoviesPhotosRepositoryTest {

    @Mock
    private lateinit var moviesPhotosStore: MoviesPhotosStore

    private lateinit var moviesPhotosRepository: MoviesPhotosRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Get Movie Photos By Name Success`() = runBlockingTest {
        val photos =
            JsonReader.constructUsingGson(MoviePhotosResponse::class.java, "movie_photos.json")
        `when`(moviesPhotosStore.getMoviePhotos("2012")).thenReturn(photos)

        moviesPhotosRepository = MoviesPhotosRepository(moviesPhotosStore)

        delay(10)

        assertEquals(moviesPhotosRepository.getMoviePhotos("2012"), photos)

        verify(moviesPhotosStore, times(1)).getMoviePhotos("2012")
    }

}
