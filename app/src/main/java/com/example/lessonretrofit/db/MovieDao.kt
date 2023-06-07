package com.example.lessonretrofit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    fun insert(movieEntity: MovieEntity)

    @Query("SELECT * FROM movieentity WHERE id = :movieId LIMIT 1")
    fun get(movieId: Int): MovieEntity?

    @Query("SELECT * FROM movieentity")
    fun getAllMovies(): List<MovieEntity>
}