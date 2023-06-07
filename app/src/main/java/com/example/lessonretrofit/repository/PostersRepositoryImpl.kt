package com.example.lessonretrofit.repository

import com.example.lessonretrofit.api.PostersApi
import com.example.lessonretrofit.data.Posters

class PostersRepositoryImpl : PostersRepository { //имплиментация интерфейса MovieRepository

    override suspend fun getBackdrops(movieId: Int): Posters? {

        val responcePosters = PostersApi.RETROFIT.getBackdrops(movieId, API_KEY).execute() //запрос через api, будет выполнен в выбранном потоке

        return if (responcePosters.isSuccessful) responcePosters.body() else null
    }
    companion object {
        private const val API_KEY = "b855a5625e1d6ec70cfc2204e22f351e"
    }
}