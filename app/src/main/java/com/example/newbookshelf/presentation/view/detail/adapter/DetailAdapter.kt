package com.example.newbookshelf.presentation.view.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newbookshelf.presentation.view.detail.MemoFragment
import com.example.newbookshelf.presentation.view.detail.ReviewFragment

class DetailAdapter(private val fragment: Fragment, private val isbn: String): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return ReviewFragment(isbn)
        }else {
            return MemoFragment(isbn)
        }
    }
}