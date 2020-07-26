package com.example.cleanarch.domain.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cleanarch.base.JsonReader
import com.example.cleanarch.base.TestCoroutineRule
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.MoviePhotosResponse
import com.example.cleanarch.data.model.Photo
import com.example.cleanarch.data.repositories.MoviesPhotosRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePhotosUseCaseTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var moviesPhotosRepository: MoviesPhotosRepository

    @Mock
    private lateinit var moviesPhotosObserver: Observer<Result<List<Photo>>>

    @Mock
    private lateinit var mockException: HttpException


    private lateinit var moviePhotosUseCase: MoviePhotosUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Get Movie Photos Success`() = runBlocking {
        val photos =
            JsonReader.constructUsingGson(MoviePhotosResponse::class.java, "movie_photos.json")
        `when`(moviesPhotosRepository.getMoviePhotos("2012")).thenReturn(photos)

        moviePhotosUseCase =
            MoviePhotosUseCase(
                moviesPhotosRepository
            )

        moviePhotosUseCase.moviePhotosLiveData.observeForever(moviesPhotosObserver)
        moviePhotosUseCase.getMoviePhoto("2012")

        delay(10)
        assert(moviePhotosUseCase.moviePhotosLiveData.value is Result.Success)
        verify(moviesPhotosRepository).getMoviePhotos("2012")
        verify(moviesPhotosObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesPhotosObserver, timeout(50))
            .onChanged(moviePhotosUseCase.moviePhotosLiveData.value)
    }

    @Test
    fun `Get Movie Photos Failure`() = runBlocking {
        `when`(moviesPhotosRepository.getMoviePhotos("2012"))
            .thenThrow(MockitoException("Some thing want to wrong"))

        moviePhotosUseCase =
            MoviePhotosUseCase(
                moviesPhotosRepository
            )

        moviePhotosUseCase.moviePhotosLiveData.observeForever(moviesPhotosObserver)
        moviePhotosUseCase.getMoviePhoto("2012")

        delay(10)
        assert(moviePhotosUseCase.moviePhotosLiveData.value is Result.Error)
        verify(moviesPhotosRepository).getMoviePhotos("2012")
        verify(moviesPhotosObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesPhotosObserver, timeout(50))
            .onChanged(moviePhotosUseCase.moviePhotosLiveData.value)
    }

    @Test
    fun `Get Movie Photos Network Failure`() = runBlocking {

        `when`(mockException.message()).thenReturn("server error")

        `when`(moviesPhotosRepository.getMoviePhotos("2012"))
            .thenThrow(mockException)

        moviePhotosUseCase =
            MoviePhotosUseCase(
                moviesPhotosRepository
            )

        moviePhotosUseCase.moviePhotosLiveData.observeForever(moviesPhotosObserver)
        moviePhotosUseCase.getMoviePhoto("2012")

        delay(10)
        assert(moviePhotosUseCase.moviePhotosLiveData.value is Result.Error)
        verify(moviesPhotosRepository).getMoviePhotos("2012")
        verify(moviesPhotosObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesPhotosObserver, timeout(50))
            .onChanged(moviePhotosUseCase.moviePhotosLiveData.value)
    }
}