package com.example.newbookshelf.presentation.view.post.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentKakaoSearchDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.adapter.KakaoAdapter
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import kotlinx.coroutines.launch

class KakaoSearchDialog(private val onSelectedPlace: OnSelectedPlace) : DialogFragment() {
    private lateinit var binding: FragmentKakaoSearchDialogBinding
    private lateinit var postViewModel: PostViewModel

    interface OnSelectedPlace {
        fun onSelectedPlace(place: String, address: String)
    }

    companion object {
        const val TAG = "KakaoSearchDialog"
    }

    private lateinit var kakaoKey: String
    private lateinit var kakaoAdapter: KakaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kakao_search_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKakaoSearchDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        kakaoKey = BuildConfig.KAKAO_REST_API_KEY
        postViewModel = (activity as HomeActivity).postViewModel
        kakaoAdapter = (activity as HomeActivity).kakaoAdapter
        kakaoAdapter.differ.submitList(emptyList())
        kakaoAdapter.setOnClickListener {
            onSelectedPlace.onSelectedPlace(it.place_name, etSearch.text.toString().trim())
            dismiss()
        }
        rvPlace.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = kakaoAdapter
        }
    }

    private fun bindViews() = with(binding){
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    searchKeyword(query)
                }
                hideKeyboard()
                true
            } else {
                false
            }
        }

        ivSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchKeyword(query)
            }
            hideKeyboard()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                postViewModel.searchPlacesResult.collect { state ->
                    when(state){
                        is Resource.Success -> {
                            state.data?.let {
                                if(it.documents.isNotEmpty()){
                                    txtEmpty.visibility = View.GONE
                                    rvPlace.visibility = View.VISIBLE
                                    kakaoAdapter.differ.submitList(it.documents)
                                }else {
                                    txtEmpty.visibility = View.VISIBLE
                                    rvPlace.visibility = View.GONE
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

    private fun searchKeyword(place: String) = with(binding){
        postViewModel.searchPlaces(kakaoKey, place)
    }

    private fun hideKeyboard() {
        val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        view?.clearFocus()
    }
}