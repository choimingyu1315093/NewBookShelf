package com.example.newbookshelf.presentation.view.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentPostBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.home.HomeFragmentDirections
import com.example.newbookshelf.presentation.view.post.adapter.GeneralAdapter
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "PostFragment"
    }

    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)
    private lateinit var generalAdapter: GeneralAdapter
    private var isGeneral = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        isGeneral = "general"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        (activity as HomeActivity).binding.cl.visibility = View.GONE
        postViewModel = (activity as HomeActivity).postViewModel
        postViewModel.postList(userIdx, 10, 1)
        generalAdapter = (activity as HomeActivity).generalAdapter
        generalAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("postIdx", it.post_idx)
            }
            findNavController().navigate(R.id.action_postFragment_to_generalDetailFragment, bundle)
        }
        rvGeneral.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = generalAdapter
        }
    }

    private fun bindViews() = with(binding){
        btnGeneral.setOnClickListener {
            isGeneral = "general"
            btnGeneral.setBackgroundResource(R.drawable.btn_main_no_10)
            btnReading.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
            rvGeneral.visibility = View.VISIBLE
            rvReading.visibility = View.GONE
        }

        btnReading.setOnClickListener {
            isGeneral = "reading"
            btnGeneral.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
            btnReading.setBackgroundResource(R.drawable.btn_main_no_10)
            rvGeneral.visibility = View.GONE
            rvReading.visibility = View.VISIBLE
        }

        faAdd.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToAddPostFragment(type = isGeneral)
            findNavController().navigate(action)
        }
    }
    
    private fun observeViewModel() = with(binding){
        postViewModel.postListResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        generalAdapter.differ.submitList(response.data.data)
                    }
                    progressBar.visibility = View.GONE
                }
                is Resource.Error -> Unit
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}