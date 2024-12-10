package com.example.newbookshelf.presentation.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentMemoBinding
import com.example.newbookshelf.presentation.view.detail.adapter.MemoAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.ReviewAdapter
import com.example.newbookshelf.presentation.view.detail.dialog.MemoDialog
import com.example.newbookshelf.presentation.view.detail.dialog.ReviewDialog
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel

class MemoFragment(private val isbn: String) : Fragment(), MemoDialog.OnDialogCloseListener {
    private lateinit var binding: FragmentMemoBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var memoAdapter: MemoAdapter

    companion object {
        const val TAG = "MemoFragment"
    }

    private lateinit var accessToken: String
    private var type = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemoBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        detailViewModel = (activity as HomeActivity).detailViewModel
        detailViewModel.getBookMemo(accessToken, isbn, type)
        memoAdapter = (activity as HomeActivity).memoAdapter
        rvMemo.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = memoAdapter
        }
    }

    private fun bindViews() = with(binding){
        btnWrite.setOnClickListener {
            val dialog = MemoDialog(isbn, this@MemoFragment)
            dialog.show(requireActivity().supportFragmentManager, "MemoDialog")
        }

        rg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb1 -> {
                    type = "all"
                    detailViewModel.getBookMemo(accessToken, isbn, type)
                }
                R.id.rb2 -> {
                    type = "mine"
                    detailViewModel.getBookMemo(accessToken, isbn, type)
                }
            }
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.getBookMemoResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    if(response.data?.data?.size != 0){
                        rvMemo.visibility = View.VISIBLE
                        tvEmpty.visibility = View.GONE

                        memoAdapter.differ.submitList(response.data?.data)
                    }else {
                        rvMemo.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    override fun memoReload(b: Boolean) {
        TODO("Not yet implemented")
    }
}