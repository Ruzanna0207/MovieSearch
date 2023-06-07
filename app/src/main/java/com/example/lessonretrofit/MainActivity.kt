package com.example.lessonretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.lessonretrofit.databinding.ActivityMainBinding
import com.example.lessonretrofit.fragments.HistoryFragment
import com.example.lessonretrofit.fragments.SearchFragment


class MainActivity : AppCompatActivity() { //имплемент. интерфейсы movieView и PostersView
    private lateinit var binding: ActivityMainBinding
    private val fragmentList =
        arrayListOf(SearchFragment.newInstance(), HistoryFragment.newInstance2())
//--------------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//--------------------------------------------------------------------------------------------------
        val adapterViewPager = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapterViewPager
        adapterViewPager.list = fragmentList
        adapterViewPager.notifyDataSetChanged()
//--------------------------------------------------------------------------------------------------
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search -> binding.viewPager.currentItem = 0

                R.id.history -> binding.viewPager.currentItem = 1
            }
            true
        }
    }
//--------------------------------------------------------------------------------------------------
    override fun onResume() {
        super.onResume()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() { // Слушатель для ViewPager
            override fun onPageSelected(position: Int) { // Изменяем текущий элемент в BottomNavigationView
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }
}


