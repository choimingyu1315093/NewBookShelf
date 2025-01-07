package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentProfileStatisticsBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.adapter.PhotoAdapter
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel

class ProfileStatisticsFragment : Fragment(), PhotoAdapter.OnClickItem {
    private lateinit var binding: FragmentProfileStatisticsBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "ProfileStatistics"
    }

    private lateinit var photoAdapter: PhotoAdapter
    private var bookImageArray = ArrayList<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileStatisticsBinding.bind(view)
        
        init()
        observeViewModel()
    }
    
    private fun init() = with(binding){
        profileViewModel = (activity as HomeActivity).profileViewModel
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.myProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        if(it.data.top1_book != null){
                            bookImageArray.add(it.data.top1_book.book_image)
                        }
                        if(it.data.top2_book != null){
                            bookImageArray.add(it.data.top2_book.book_image)
                        }
                        if(it.data.top3_book != null){
                            bookImageArray.add(it.data.top3_book.book_image)
                        }

                        if(bookImageArray.isNotEmpty()){
                            tvName.text = "${it.data.user_name} 님의 인생 책"
                            photoAdapter = PhotoAdapter(bookImageArray, requireContext(), this@ProfileStatisticsFragment, 1)
                            rvTopBook.apply {
                                layoutManager = GridLayoutManager(requireContext(), 3)
                                adapter = photoAdapter
                            }
                        }else {
                            tvName.visibility = View.GONE
                            rvTopBook.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    override fun selectBook(isPhoto: Boolean) {
        Log.d(TAG, "selectBook: 기능없음")
    }
}