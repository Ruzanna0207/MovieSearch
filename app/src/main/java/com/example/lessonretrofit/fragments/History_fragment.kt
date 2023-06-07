package com.example.lessonretrofit.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lessonretrofit.AdapterHistory
import com.example.lessonretrofit.databinding.HisoryOfSearchBinding
import com.example.lessonretrofit.db.MovieEntity
import com.example.lessonretrofit.vm.MovieViewModel
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private lateinit var bindingHistory: HisoryOfSearchBinding
    private lateinit var adapterHistory: AdapterHistory
    private var films = listOf<MovieEntity>()
    private val viewModel: MovieViewModel by viewModels()

//==================================================================================================
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHistory = HisoryOfSearchBinding.inflate(inflater, container, false)
        return bindingHistory.root
    }
//==================================================================================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterHistory = AdapterHistory()  //создание адаптера для отображения истории
        bindingHistory.historyFilms.adapter = adapterHistory
        adapterHistory.dataHistory = films
    }
//==================================================================================================
    override fun onResume() { //будет использоваться для динамического изменения списка истории
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch { //создание корутины привязанной к жизненному циклу фрагмента
                viewModel.loadMovies().collect { movies -> //реализация stateFlow из viewModel
                    Log.i("main1", "История на потоке -> ${Thread.currentThread().name}")
                    films = movies
                    films.toSet()
                    Log.d("main1", " все фильмы = $movies")
                    adapterHistory.dataHistory = films.toList()
                    adapterHistory.notifyDataSetChanged()
                }
            }
//--------------------------------------------------------------------------------------------------
        bindingHistory.deleteButton.setOnClickListener {
            viewModel.deleteAll() //функция прописанная во vewModel, удаляет всю историю
            adapterHistory.dataHistory = listOf() //для моментального отображения очистки истории в адаптер передается пустой список
            adapterHistory.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance2() = HistoryFragment()
    }
}
