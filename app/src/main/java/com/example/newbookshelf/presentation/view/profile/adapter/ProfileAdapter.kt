package com.example.newbookshelf.presentation.view.profile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newbookshelf.presentation.view.profile.ProfileActiveFragment
import com.example.newbookshelf.presentation.view.profile.ProfileMemoFragment
import com.example.newbookshelf.presentation.view.profile.ProfileReadingFragment

class ProfileAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return ProfileActiveFragment()
        }else if(position == 1){
            return ProfileMemoFragment()
        }else {
            return ProfileReadingFragment()
        }
    }
}