package com.example.newbookshelf.presentation.view.post

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.example.newbookshelf.presentation.view.post.ReadingDetailFragment.Companion
import com.example.newbookshelf.presentation.view.post.adapter.GeneralAdapter
import com.example.newbookshelf.presentation.view.post.adapter.ReadingClassAdapter
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import java.util.Locale

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "PostFragment"
    }

    private lateinit var generalAdapter: GeneralAdapter
    private var isGeneral = "general"

    private lateinit var readingClassAdapter: ReadingClassAdapter

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
        postViewModel.postList(10, 1)
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

        readingClassAdapter = (activity as HomeActivity).readingClassAdapter
        readingClassAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putInt("readingClassIdx", it.club_post_idx)
            }
            findNavController().navigate(R.id.action_postFragment_to_readingDetailFragment, bundle)
        }
        rvReading.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = readingClassAdapter
        }

        val spList = listOf("등록순", "거리순")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spList)
        adapter.setDropDownViewResource(R.layout.item_dropdown)
        spFilter.adapter = adapter
    }

    private fun bindViews() = with(binding){
        btnGeneral.setOnClickListener {
            postViewModel.postList(10, 1)
            isGeneral = "general"
            btnGeneral.setBackgroundResource(R.drawable.btn_main_no_10)
            btnReading.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
            rvGeneral.visibility = View.VISIBLE
            rvReading.visibility = View.GONE
            spFilter.visibility = View.GONE
        }

        btnReading.setOnClickListener {
            postViewModel.readingClassList("", "latest", 10, 1)
            isGeneral = "reading"
            btnGeneral.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
            btnReading.setBackgroundResource(R.drawable.btn_main_no_10)
            rvGeneral.visibility = View.GONE
            rvReading.visibility = View.VISIBLE
            spFilter.visibility = View.VISIBLE
        }

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(parent?.getItemAtPosition(position).toString() != "등록순"){
                    postViewModel.readingClassList("", "latest", 10, 1)
                }else {
                    postViewModel.readingClassList("", "distance", 10, 1)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
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
                        if(response.data.data.isNotEmpty()){
                            generalAdapter.differ.submitList(response.data.data)
                            clNo.visibility = View.GONE
                            rvGeneral.visibility = View.VISIBLE
                        }else {
                            clNo.visibility = View.VISIBLE
                            rvGeneral.visibility = View.GONE
                            tvEmpty.text = "게시글을 작성해 보세요."
                        }
                    }
                    progressBar.visibility = View.GONE
                }
                is Resource.Error -> Unit
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        postViewModel.readingClassListResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        if(response.data.data.isNotEmpty()){
                            readingClassAdapter.differ.submitList(response.data.data)
                            clNo.visibility = View.GONE
                            rvReading.visibility = View.VISIBLE
                        }else {
                            clNo.visibility = View.VISIBLE
                            rvReading.visibility = View.GONE
                            tvEmpty.text ="독서 모임을 만들어 보세요."
                        }
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