package com.example.lessonretrofit.data

import com.google.gson.annotations.SerializedName

data class Posters(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("posters")
    val posters: List<Poster?>?
)