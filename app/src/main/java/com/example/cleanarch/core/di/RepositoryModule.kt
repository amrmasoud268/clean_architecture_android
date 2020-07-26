package com.example.cleanarch.core.di

import com.example.cleanarch.data.repositories.MoviesPhotosRepository
import com.example.cleanarch.data.repositories.MoviesRepository
import org.koin.dsl.module

val repoModule = module {
    factory { MoviesRepository(get()) }
    factory { MoviesPhotosRepository(get()) }
}