package com.example.newbookshelf.presentation.view.profile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newbookshelf.presentation.view.profile.ProfileBookProcessFragment

class ProfileBookAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return ProfileBookProcessFragment("읽고 싶은 책")
        }else if(position == 1){
            return ProfileBookProcessFragment("읽고 있는 책")
        }else {
            return ProfileBookProcessFragment("읽은 책")
        }
    }
}