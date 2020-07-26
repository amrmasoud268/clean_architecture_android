package com.example.cleanarch.core.di

import com.example.cleanarch.domain.movies.MoviePhotosUseCase
import com.example.cleanarch.domain.movies.MoviesSearchUseCase
import com.example.cleanarch.domain.movies.MoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { MoviesUseCase(get()) }
    factory { MoviesSearchUseCase(get()) }
    factory { MoviePhotosUseCase(get()) }
}