package com.example.cleanarch.core.di

import com.example.cleanarch.data.apis.MoviePhotosAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideMoviePhotosAPIs(get()) }
}

fun provideMoviePhotosAPIs(retrofit: Retrofit): MoviePhotosAPI {
    return retrofit.create(MoviePhotosAPI::class.java)
}