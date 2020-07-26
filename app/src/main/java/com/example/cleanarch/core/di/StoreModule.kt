package com.example.cleanarch.core.di

import com.example.cleanarch.data.store.MoviesPhotosStore
import com.example.cleanarch.data.store.MoviesStore
import com.example.cleanarch.data.store.MoviesPhotosStoreImpl
import com.example.cleanarch.data.store.MoviesStoreImpl
import org.koin.dsl.module

val storeModule = module {
    factory { MoviesStoreImpl(get()) as MoviesStore }
    factory { MoviesPhotosStoreImpl(get()) as MoviesPhotosStore}
}