package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentProfileBookBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileBookAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileBookFragment : Fragment() {
    private lateinit var binding: FragmentProfileBookBinding

    companion object {
        const val TAG = "ProfileBookFragment"
    }

    private lateinit var accessToken: String
    private lateinit var profileBookAdapter: ProfileBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBookBinding.bind(view)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        tvName.text = "${BookShelfApp.prefs.getNickname("nickname", "")} 님의 서재"
        profileBookAdapter = ProfileBookAdapter(requireParentFragment())
        vpLike.adapter = profileBookAdapter
        TabLayoutMediator(tlLike, vpLike){tab, position ->
            when(position){
                0 -> {
                    tab.text = "읽고 싶은 책"
                }
                1 -> {
                    tab.text = "읽고 있는 책"
                }
                2 -> {
                    tab.text = "읽은 책"
                }
            }
        }.attach()
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}