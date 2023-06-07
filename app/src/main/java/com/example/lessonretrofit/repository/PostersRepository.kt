package com.example.lessonretrofit.repository

import com.example.lessonretrofit.data.Posters

interface PostersRepository { //отвечает за получение и хранение данных

    suspend fun getBackdrops(movieId: Int): Posters?
}