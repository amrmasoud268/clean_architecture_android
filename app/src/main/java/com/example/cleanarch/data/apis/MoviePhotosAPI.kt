package com.example.cleanarch.data.apis

import com.example.cleanarch.data.apis.NetworkConstance.map
import com.example.cleanarch.data.model.MoviePhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MoviePhotosAPI {
    @GET("?method=flickr.photos.search")
    suspend fun getMoviePhotos(
        @Query("text") name: String,
        @QueryMap defaultMap: Map<String, String> = map
    ): MoviePhotosResponse

}