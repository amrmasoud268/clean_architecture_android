package com.example.cleanarch.core.di

import com.example.cleanarch.ui.view_models.MoviePhotoViewModel
import com.example.cleanarch.ui.view_models.MovieViewModel
import com.example.cleanarch.ui.view_models.SearchMoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { SearchMoviesViewModel(get()) }
    viewModel { MoviePhotoViewModel(get()) }
}