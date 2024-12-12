package com.example.newbookshelf.presentation.view.profile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newbookshelf.presentation.view.profile.ProfileActiveFragment
import com.example.newbookshelf.presentation.view.profile.ProfileMemoFragment

class ProfileAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return ProfileActiveFragment()
        }else {
            return ProfileMemoFragment()
        }
    }
}