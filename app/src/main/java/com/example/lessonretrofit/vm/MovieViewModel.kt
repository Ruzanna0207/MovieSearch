package com.example.lessonretrofit.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessonretrofit.data.Movie
import com.example.lessonretrofit.db.MovieEntity
import com.example.lessonretrofit.repository.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _currentMovie = MutableStateFlow<Movie?>(null) //stateFlow для получения и хранения информации
    private val repository = MovieRepositoryImpl(application)  // реализация model repository, возвращающего данные полученные через api
    val currentMovie: StateFlow<Movie?> = _currentMovie

//--------------------------------------------------------------------------------------------------
    fun loadMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) { //создание корутины привязанной к жизненному циклу VM
            val movie = repository.getMovieDetails(movieId)
            _currentMovie.value = movie //в поток данных передаются знач-я полученные через api
            Log.d("Movie", "${currentMovie.value}")
        }
    }
//--------------------------------------------------------------------------------------------------
        fun loadMovies(): Flow<List<MovieEntity>> = flow {
        emit(repository.getAllMovies()) //emit, отдает List<MovieEntity> из репозитория.
    }.flowOn(Dispatchers.IO)
    //При начале потока, данные будут переданы в любой коллектор, который подпиш-ся на этот поток
//--------------------------------------------------------------------------------------------------
    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAll()
        }
    }
}
