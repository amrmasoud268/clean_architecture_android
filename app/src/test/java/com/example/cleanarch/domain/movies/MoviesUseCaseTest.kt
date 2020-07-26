package com.example.cleanarch.domain.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cleanarch.base.JsonReader
import com.example.cleanarch.base.TestCoroutineRule
import com.example.cleanarch.data.Result
import com.example.cleanarch.data.model.Movie
import com.example.cleanarch.data.model.MoviesResponse
import com.example.cleanarch.data.repositories.MoviesRepository
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesUseCaseTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var moviesObserver: Observer<Result<List<Movie>>>
    private lateinit var moviesUseCass: MoviesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Get Movies Success`() = runBlocking {
        val movies = JsonReader.constructUsingGson(MoviesResponse::class.java, "movies.json").movies

        `when`(moviesRepository.getMovies()).thenReturn(movies)

        moviesUseCass =
            MoviesUseCase(moviesRepository)

        moviesUseCass.moviesLiveData.observeForever(moviesObserver)
        moviesUseCass.getMovies()

        delay(10)
        assert(moviesUseCass.moviesLiveData.value is Result.Success)
        verify(moviesRepository).getMovies()
        verify(moviesObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesObserver, timeout(50)).onChanged(moviesUseCass.moviesLiveData.value)

    }

    @Test
    fun `Get Movies Failure`() = runBlocking {

        `when`(moviesRepository.getMovies()).thenThrow(MockitoException("Some thing want to wrong"))

        moviesUseCass =
            MoviesUseCase(moviesRepository)

        moviesUseCass.moviesLiveData.observeForever(moviesObserver)
        moviesUseCass.getMovies()

        delay(10)
        assert(moviesUseCass.moviesLiveData.value is Result.Error)
        verify(moviesRepository).getMovies()
        verify(moviesObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesObserver, timeout(50)).onChanged(moviesUseCass.moviesLiveData.value)

    }

}