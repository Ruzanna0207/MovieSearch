package com.example.lessonretrofit.api

import com.example.lessonretrofit.data.Posters
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostersApi {
    @GET("movie/{movieId}/images") //получение данных для постеров при помощи retrofit

    fun getBackdrops(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
    ): Call<Posters>

    companion object {
        val RETROFIT: PostersApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(PostersApi::class.java)
    }
}