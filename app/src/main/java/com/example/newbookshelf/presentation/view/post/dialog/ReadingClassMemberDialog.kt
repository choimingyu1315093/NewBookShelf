package com.example.newbookshelf.presentation.view.post.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentReadingClassMemberDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.adapter.MemberAdapter
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import kotlinx.coroutines.job

class ReadingClassMemberDialog(private val readingClassIdx: Int) : DialogFragment() {
    private lateinit var binding: FragmentReadingClassMemberDialogBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "ReadingClassMemberDialog"
    }

    private lateinit var memberAdapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reading_class_member_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReadingClassMemberDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        postViewModel = (activity as HomeActivity).postViewModel
        postViewModel.readingClassMemberList(readingClassIdx, 30, 1)
        memberAdapter = (activity as HomeActivity).memberAdapter
        rvMember.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = memberAdapter
        }
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() = with(binding){
        postViewModel.readingClassMemberListResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        memberAdapter.differ.submitList(response.data.data)
                    }else {
                        txtEmpty.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}