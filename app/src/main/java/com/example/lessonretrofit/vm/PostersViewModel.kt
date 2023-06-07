package com.example.lessonretrofit.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessonretrofit.data.Posters
import com.example.lessonretrofit.repository.PostersRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostersViewModel : ViewModel() {
    private val _currentPosters = MutableStateFlow<Posters?>(null) //stateFlow для получения и хранения информации

    private val postersRepository = PostersRepositoryImpl()  // реализация model repository, возвращающего данные полученные через api

    val currentPosters: StateFlow<Posters?> = _currentPosters // хранение информации для использования
//--------------------------------------------------------------------------------------------------
    fun loadPosters(movieId: Int) {
        val scope = viewModelScope.launch(Dispatchers.IO) { //создание корутины привязанной к жизненному циклу VM
            val posters = postersRepository.getBackdrops(movieId)
            _currentPosters.value = posters //в поток данных передаются знач-я полученные через api

            Log.d("Movie", "${currentPosters.value}")
        }
    }
}