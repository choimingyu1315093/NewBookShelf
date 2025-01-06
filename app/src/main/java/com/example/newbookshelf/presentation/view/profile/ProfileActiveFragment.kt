package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentProfileActiveBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileActiveAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileAdapter
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActiveFragment : Fragment() {
    private lateinit var binding: FragmentProfileActiveBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileActiveAdapter: ProfileActiveAdapter

    companion object {
        const val TAG = "ProfileActiveFragment"
    }

    private var userIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_active, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileActiveBinding.bind(view)

        init()
        observeViewModel()
    }

    private fun init() = with(binding){
        userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)
        profileViewModel = (activity as HomeActivity).profileViewModel
        profileActiveAdapter = (activity as HomeActivity).profileActiveAdapter
        rvActive.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = profileActiveAdapter
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.profileActivity(userIdx).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.data.let {
                        if(it!!.isNotEmpty()){
                            clNoActive.visibility = View.GONE
                            rvActive.visibility = View.VISIBLE
                            profileActiveAdapter.differ.submitList(it)
                        }else {
                            clNoActive.visibility = View.VISIBLE
                            rvActive.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}