package com.example.cleanarch.core.di

import android.content.res.Resources
import com.example.cleanarch.core.utils.JsonResourceReader
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val AppModule = module {
    single { provideJsonResourceReader(androidContext().resources) }
    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}

fun provideJsonResourceReader(resource: Resources) = JsonResourceReader(resource)

fun provideGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}

fun provideHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()

    return okHttpClientBuilder.build()
}

fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.flickr.com/services/rest/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(client)
        .build()
}