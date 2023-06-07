package com.example.lessonretrofit.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDataBase : RoomDatabase() { //абстрактный класс для создания БД
    abstract fun movieDao(): MovieDao           //имеющий абстрактн.фун-ю, MovieDao для хранения функций выполн-ых в БД
}