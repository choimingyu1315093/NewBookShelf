package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentProfileBookProcessBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.adapter.MyBookListAdapter
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel

class ProfileBookProcessFragment(private val type: String) : Fragment() {
    private lateinit var binding: FragmentProfileBookProcessBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "ProfileBookProcessFragment"
    }

    private lateinit var accessToken: String
    private lateinit var myBookListAdapter: MyBookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        val category = when (type) {
            "읽고 싶은 책" -> "wish"
            "읽고 있는 책" -> "reading"
            else -> "read"
        }
        val emptyMessage = when (type) {
            "읽고 싶은 책" -> "읽고 싶은 책이 없어요."
            "읽고 있는 책" -> "읽고 있는 책이 없어요."
            else -> "읽은 책이 없어요."
        }

        profileViewModel.myBookList(accessToken, category).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        if (response.data.data.isNotEmpty()) {
                            binding.rvBook.visibility = View.VISIBLE
                            binding.clNoLike.visibility = View.GONE
                            myBookListAdapter.differ.submitList(response.data.data)
                        } else {
                            binding.rvBook.visibility = View.GONE
                            binding.clNoLike.visibility = View.VISIBLE
                            binding.tvEmpty.text = emptyMessage
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_book_process, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBookProcessBinding.bind(view)

        init()
    }

    private fun init() = with(binding){
        rvBook.visibility = View.GONE
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        profileViewModel = (activity as HomeActivity).profileViewModel
        myBookListAdapter = (activity as HomeActivity).myBookListAdapter
        rvBook.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myBookListAdapter
        }
    }
}