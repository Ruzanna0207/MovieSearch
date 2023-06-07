package com.example.lessonretrofit.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.lessonretrofit.data.Movie

@Entity  //объекты, которые будут сохраняться в базе данных
data class MovieEntity(
    @PrimaryKey(false) val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("release_data") val releaseData: String,
    @ColumnInfo("budget") val budget: Int,
) {
    @Ignore
    fun toMovie(): Movie {
        return Movie(id, title, releaseData, budget)
    }
}