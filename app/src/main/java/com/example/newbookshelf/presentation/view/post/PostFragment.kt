package com.example.newbookshelf.presentation.view.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentPostBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.home.HomeFragmentDirections

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding

    companion object {
        const val TAG = "PostFragment"
    }

    private var isGeneral = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)

        (activity as HomeActivity).binding.cl.visibility = View.GONE

        init()
        bindViews()
    }

    private fun init() = with(binding){

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
}