package com.example.lessonretrofit.repository

import android.content.Context
import androidx.room.Room
import com.example.lessonretrofit.api.MovieApi
import com.example.lessonretrofit.data.Movie
import com.example.lessonretrofit.db.MovieDataBase
import com.example.lessonretrofit.db.MovieEntity

class MovieRepositoryImpl(private val context: Context) : MovieRepository { //имплиментация интерфейса MovieRepository

    private val dataBase = Room
        .databaseBuilder(context, MovieDataBase::class.java, "movie_database") //создание БД
        .build()
//--------------------------------------------------------------------------------------------------
    fun getAllMovies(): List<MovieEntity> {
        return dataBase.movieDao().getAllMovies()
    }
//--------------------------------------------------------------------------------------------------
fun clearAll() {
    dataBase.clearAllTables()
}
//--------------------------------------------------------------------------------------------------
    override suspend fun getMovieDetails(movieId: Int): Movie? {
        val savedMovieEntity = dataBase.movieDao().get(movieId) //запрос

        return if (savedMovieEntity != null) {
            savedMovieEntity.toMovie()
        } else {
            val responce = MovieApi.INSTANCE.getMovieDetails(movieId, API_KEY)
                .execute() //запрос через api, будет выполнен в выбранном потоке

            if (responce.isSuccessful) {
                val movie = responce.body()

                if (movie != null) {
                    dataBase.movieDao().insert(movie.toMovieEntity())
                }
                movie
            } else {
                null
            }
        }
    }

    companion object {
        private const val API_KEY = "b855a5625e1d6ec70cfc2204e22f351e"
    }
}
