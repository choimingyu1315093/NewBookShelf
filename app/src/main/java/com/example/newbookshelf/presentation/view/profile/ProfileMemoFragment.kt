package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentProfileMemoBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileMemoAdapter
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel

class ProfileMemoFragment : Fragment() {
    private lateinit var binding: FragmentProfileMemoBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileMemoAdapter: ProfileMemoAdapter

    companion object {
        const val TAG = "ProfileMemoFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileMemoBinding.bind(view)

        init()
        observeViewModel()
    }

    private fun init() = with(binding){
        profileViewModel = (activity as HomeActivity).profileViewModel
        profileMemoAdapter = (activity as HomeActivity).profileMemoAdapter
        rvMemo.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = profileMemoAdapter
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.profileMemo().observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.data.let {
                        if(it!!.isNotEmpty()){
                            clNoActive.visibility = View.GONE
                            rvMemo.visibility = View.VISIBLE
                            profileMemoAdapter.differ.submitList(it)
                        }else {
                            clNoActive.visibility = View.VISIBLE
                            rvMemo.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }
    }
}