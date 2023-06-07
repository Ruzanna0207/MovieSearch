package com.example.lessonretrofit.repository

import com.example.lessonretrofit.data.Movie

interface MovieRepository { //отвечает за получение и хранение данных

    suspend fun getMovieDetails(movieId: Int): Movie?
}
