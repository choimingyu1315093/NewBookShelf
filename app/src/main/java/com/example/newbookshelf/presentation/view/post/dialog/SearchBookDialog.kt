package com.example.newbookshelf.presentation.view.post.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentSearchBookDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.view.post.adapter.PostSearchBookAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileSearchBookAdapter
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel
import kotlinx.coroutines.launch

class SearchBookDialog(private val onSelectedBook: OnSelectedBook, private val type: String) : DialogFragment() {
    private lateinit var binding: FragmentSearchBookDialogBinding
    private lateinit var homeViewModel: HomeViewModel

    interface OnSelectedBook {
        fun onSelectedBook(book: SearchBookResult)
    }

    companion object {
        const val TAG = "SearchBookDialog"
    }

    private lateinit var profileSearchBookAdapter: ProfileSearchBookAdapter
    private lateinit var postSearchBookAdapter: PostSearchBookAdapter

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
        return inflater.inflate(R.layout.fragment_search_book_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBookDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        homeViewModel = (activity as HomeActivity).homeViewModel
        profileSearchBookAdapter = (activity as HomeActivity).profileSearchBookAdapter
        postSearchBookAdapter = (activity as HomeActivity).postSearchBookAdapter
        if(type == "profile"){
            profileSearchBookAdapter.differ.submitList(emptyList())
            profileSearchBookAdapter.setOnItemClickListener {
                dismiss()
                onSelectedBook.onSelectedBook(it)
            }
            rvBook.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = profileSearchBookAdapter
            }
        }else {
            postSearchBookAdapter.differ.submitList(emptyList())
            postSearchBookAdapter.setOnItemClickListener {
                dismiss()
                onSelectedBook.onSelectedBook(it)
            }
            rvBook.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = postSearchBookAdapter
            }
        }
    }

    private fun bindViews() = with(binding){
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchKeyword(etSearch.text.toString().trim())
                hideKeyboard()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        ivSearch.setOnClickListener {
            searchKeyword(etSearch.text.toString().trim())
            hideKeyboard()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.profileSearchBook.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        if(!it.data.general_result.isNullOrEmpty()){
                            txtEmpty.visibility = View.GONE
                            rvBook.visibility = View.VISIBLE
                            profileSearchBookAdapter.differ.submitList(it.data.general_result)
                        }else if(!it.data.popular_result.isNullOrEmpty()){
                            txtEmpty.visibility = View.GONE
                            rvBook.visibility = View.VISIBLE
                            profileSearchBookAdapter.differ.submitList(it.data.popular_result)
                        }else {
                            txtEmpty.visibility = View.VISIBLE
                            rvBook.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.postSearchBook.collect { state ->
                    when(state){
                        is Resource.Success -> {
                            state.data?.let {
                                if(!it.data.general_result.isNullOrEmpty()){
                                    txtEmpty.visibility = View.GONE
                                    rvBook.visibility = View.VISIBLE
                                    postSearchBookAdapter.differ.submitList(it.data.general_result)
                                }else if(!it.data.popular_result.isNullOrEmpty()){
                                    txtEmpty.visibility = View.GONE
                                    rvBook.visibility = View.VISIBLE
                                    postSearchBookAdapter.differ.submitList(it.data.popular_result)
                                }else {
                                    txtEmpty.visibility = View.VISIBLE
                                    rvBook.visibility = View.GONE
                                }
                            }
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> Unit
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun searchKeyword(book: String) = with(binding){
        if(type == "profile"){
            homeViewModel.getProfileSearchBook(book)
        }else {
            homeViewModel.getPostSearchBook(book)
        }
    }

    private fun hideKeyboard() {
        val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        view?.clearFocus()
    }
}