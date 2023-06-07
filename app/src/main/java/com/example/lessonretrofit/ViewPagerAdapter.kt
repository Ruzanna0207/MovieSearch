package com.example.lessonretrofit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity ) : FragmentStateAdapter(fragment) {
    var list = ArrayList<Fragment>()

    override fun getItemCount(): Int {
      return list.size
    }

    override fun createFragment(position: Int): Fragment {
      return list[position]
    }
}