package com.example.cleanarch.data.repositories

import com.example.cleanarch.base.TestCoroutineRule
import com.example.cleanarch.data.store.MoviesStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class MoviesRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var moviesStore: MoviesStore


    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Get Movies Success`() = runBlocking {
    }

    @Test
    fun `Get Movies By Name Failure`() = runBlocking {
    }
}
