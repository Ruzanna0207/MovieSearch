package com.example.lessonretrofit.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lessonretrofit.PosterAdapter
import com.example.lessonretrofit.data.Movie
import com.example.lessonretrofit.data.Poster
import com.example.lessonretrofit.data.Posters
import com.example.lessonretrofit.databinding.SearchFragmentBinding
import com.example.lessonretrofit.vm.MovieViewModel
import com.example.lessonretrofit.vm.PostersViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var bindingSearch: SearchFragmentBinding
    private lateinit var adapter: PosterAdapter
    private var list = listOf<Poster?>()

//==================================================================================================
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingSearch = SearchFragmentBinding.inflate(inflater, container, false)
        return bindingSearch.root
    }
//==================================================================================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MovieViewModel by viewModels() //реализация viewModel
        val postersViewModel: PostersViewModel by viewModels()

        val preferences = requireContext().getSharedPreferences("MovieId", Context.MODE_PRIVATE) //sharedP. для сохранения id
        val preferencesClick = requireContext().getSharedPreferences("ClickCount", Context.MODE_PRIVATE) //sharedP. для счетчика нажатий на кнопку

        adapter = PosterAdapter() //создание адаптера для rec.view
        adapter.data = list
        bindingSearch.recyclerViewPosters.adapter = adapter
//--------------------------------------------------------------------------------------------------
        viewLifecycleOwner.lifecycleScope.launch { //создание корутины привязанной к жизненному циклу VM
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentMovie.collect { movie -> //реализация stateFlow из viewModel
                    setMovie(movie)
                    Log.d("main1", " movie = $movie")
                    Log.i("main1", "Это поток 1 -> ${Thread.currentThread().name}")
                }
            }
        }
        lifecycleScope.launch { //создание корутины привязанной к жизненному циклу VM
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postersViewModel.currentPosters.collect { posters -> //реализация stateFlow из viewModel
                    setPosters(posters)
                    Log.d("main1", " posters = $posters")
                    Log.i("main1", "Это поток 2 -> ${Thread.currentThread().name}")
                }
            }
        }
//--------------------------------------------------------------------------------------------------
        bindingSearch.loadButton.setOnClickListener {
            val movieId =
                bindingSearch.movieIdText.text.toString().toIntOrNull() //id для поиска фильмов

            if (movieId != null) {
                viewModel.loadMovie(movieId)
                postersViewModel.loadPosters(movieId)
                preferences.edit().putInt("id", movieId).apply() //сохранение id
            }
            var count = 0 //счетчик нажатий
            count++

            val countClick = preferencesClick.getInt("click", count) //сохранение данных счетчика
            preferencesClick.edit().putInt("click", countClick + 1).apply()

            val toast = Toast.makeText(requireContext(), "Вы искали фильмы $countClick раз", Toast.LENGTH_SHORT)
            toast.show()
        }
//--------------------------------------------------------------------------------------------------
        val savedId = preferences.getInt("id", Int.MIN_VALUE)
        if (savedId != Int.MIN_VALUE) { //условия для отображения сохраненного id
            viewModel.loadMovie(savedId)
            postersViewModel.loadPosters(savedId)
        }
    }
//--------------------------------------------------------------------------------------------------
    private fun setMovie(movie: Movie?) { //используется для обновления данных в представлении
        if (movie == null) {
            return
        }
        bindingSearch.movieTitleValue.text = movie.title
        bindingSearch.movieReleaseValue.text = movie.releaseData
        bindingSearch.movieBudgetValue.text = movie.budget.toString() + " $"
    }
//--------------------------------------------------------------------------------------------------
    private fun setPosters(posters: Posters?) { //используется для обновления данных в представлении
    if (posters == null) {
        return
    }
    val newList = posters.posters
    if (newList != null) {
        list = newList
        adapter.data = list
        bindingSearch.recyclerViewPosters.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
//--------------------------------------------------------------------------------------------------
companion object {
    fun newInstance() = SearchFragment()
}
}