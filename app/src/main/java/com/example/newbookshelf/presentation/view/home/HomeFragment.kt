package com.example.newbookshelf.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentHomeBinding
import com.example.newbookshelf.presentation.view.home.adapter.AttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NotificationAdapter
import com.example.newbookshelf.presentation.view.home.adapter.WeekBestsellerAdapter
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var weekBestsellerAdapter: WeekBestsellerAdapter
    private lateinit var newBestsellerAdapter: NewBestsellerAdapter
    private lateinit var attentionBestsellerAdapter: AttentionBestsellerAdapter

    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var accessToken: String
    private var searchTarget = "Book"
    private var categoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        init()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        homeViewModel = (activity as HomeActivity).homeViewModel

        weekBestsellerAdapter = (activity as HomeActivity).weekBestsellerAdapter
        weekBestsellerAdapter.setOnClickListener {
//            val bundle = Bundle().apply {
//                putParcelable("book", it)
//            }
//            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)

            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(book = it, searchBook = null)
            findNavController().navigate(action)
        }

        newBestsellerAdapter = (activity as HomeActivity).newBestsellerAdapter
        newBestsellerAdapter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(book = it, searchBook = null)
            findNavController().navigate(action)
        }

        attentionBestsellerAdapter = (activity as HomeActivity).attentionBestseller
        attentionBestsellerAdapter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(book = it, searchBook = null)
            findNavController().navigate(action)
        }

        rvWeekBestseller.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = weekBestsellerAdapter
        }

        rvNewBestseller.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = newBestsellerAdapter
        }

        rvAttentionBestseller.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = attentionBestsellerAdapter
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.weekBestseller(searchTarget, categoryId).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        weekBestsellerAdapter.isDetail = false
                        weekBestsellerAdapter.differ.submitList(it.item)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }

        homeViewModel.newBestseller(searchTarget, categoryId).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        newBestsellerAdapter.isDetail = false
                        newBestsellerAdapter.differ.submitList(it.item)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }

        homeViewModel.attentionBestseller(searchTarget, categoryId).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        attentionBestsellerAdapter.isDetail = false
                        attentionBestsellerAdapter.differ.submitList(it.item)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }
    }

    private fun showProgress() = with(binding){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() = with(binding){
        progressBar.visibility = View.GONE
    }
}