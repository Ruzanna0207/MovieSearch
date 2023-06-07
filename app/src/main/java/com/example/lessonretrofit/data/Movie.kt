package com.example.lessonretrofit.data

import com.example.lessonretrofit.db.MovieEntity
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseData: String,
    @SerializedName("budget") val budget: Int,)
{
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(id, title, releaseData, budget)
    }
}
