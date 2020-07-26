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
class MoviesSearchUseCaseTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var moviesObserver: Observer<Result<Map<Int, List<Movie>>>>
    private lateinit var moviesSearchUseCase: MoviesSearchUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Search Movies Success`() = runBlocking {
        val movies = JsonReader.constructUsingGson(MoviesResponse::class.java, "movies.json").movies

        `when`(moviesRepository.getMovies("2012")).thenReturn(movies)

        moviesSearchUseCase =
            MoviesSearchUseCase(
                moviesRepository
            )

        moviesSearchUseCase.moviesMapLiveData.observeForever(moviesObserver)
        moviesSearchUseCase.searchAndFilterMovies("2012")

        delay(10)
        assert(moviesSearchUseCase.moviesMapLiveData.value is Result.Success)
        verify(moviesRepository).getMovies("2012")
        verify(moviesObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesObserver, timeout(50))
            .onChanged(moviesSearchUseCase.moviesMapLiveData.value)

    }

    @Test
    fun `Search Movies Failure`() = runBlocking {
        `when`(moviesRepository.getMovies("2012")).thenThrow(MockitoException("Some thing want to wrong"))

        moviesSearchUseCase =
            MoviesSearchUseCase(
                moviesRepository
            )

        moviesSearchUseCase.moviesMapLiveData.observeForever(moviesObserver)
        moviesSearchUseCase.searchAndFilterMovies("2012")

        delay(10)
        assert(moviesSearchUseCase.moviesMapLiveData.value is Result.Error)
        verify(moviesRepository).getMovies("2012")
        verify(moviesObserver, timeout(50)).onChanged(Result.Loading)
        verify(moviesObserver, timeout(50))
            .onChanged(moviesSearchUseCase.moviesMapLiveData.value)
    }

}