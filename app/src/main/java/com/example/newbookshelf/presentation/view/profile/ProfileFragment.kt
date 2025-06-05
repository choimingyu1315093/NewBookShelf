package com.example.newbookshelf.presentation.view.profile

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentProfileBinding
import com.example.newbookshelf.presentation.view.detail.adapter.DetailAdapter
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileAdapter
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "ProfileFragment"
    }

    private lateinit var accessToken: String
    private lateinit var profileAdapter: ProfileAdapter
    private var topBook: ArrayList<String> = arrayListOf()
    private var topBookImageList: ArrayList<String> = arrayListOf()
    private var topBookIsbnList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        profileViewModel.myProfile()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        (activity as HomeActivity).binding.cl.visibility = View.GONE
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        profileViewModel = (activity as HomeActivity).profileViewModel
    }

    private fun bindViews() = with(binding){
        btnBook.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileBookFragment)
        }

        btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileChangeFragment)
        }
    }

    private fun observeViewModel() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                profileViewModel.myProfileInfo.collect { response ->
                    when(response){
                        is Resource.Success -> {
                            progressBar.visibility = View.GONE
                            response.data?.let {
                                tvName.text = it.data.user_name
                                BookShelfApp.prefs.setNickname("nickname", it.data.user_name)

                                if(it.data.user_description == ""){
                                    tvOneLine.text = "한 줄 메시지를 입력해주세요."
                                }else {
                                    tvOneLine.text = it.data.user_description
                                }

                                setColoredText(tvFriend, it.data.relation_count)
                                setColoredText(tvRequest, it.data.relation_request_count)

                                topBook.clear()
                                topBookImageList.clear()
                                topBookImageList.add("")
                                topBookIsbnList.clear()
                                topBookIsbnList.add("")
                                if(it.data.top1_book != null){
                                    topBook.add(it.data.top1_book.book_image)
                                    topBookImageList.add(it.data.top1_book.book_image)
                                    topBookIsbnList.add(it.data.top1_book.book_isbn)
                                }
                                if(it.data.top2_book != null){
                                    topBook.add(it.data.top2_book.book_image)
                                    topBookImageList.add(it.data.top2_book.book_image)
                                    topBookIsbnList.add(it.data.top2_book.book_isbn)
                                }
                                if(it.data.top3_book != null){
                                    topBook.add(it.data.top3_book.book_image)
                                    topBookImageList.add(it.data.top3_book.book_image)
                                    topBookIsbnList.add(it.data.top3_book.book_isbn)
                                }

                                profileViewModel.userName.value = it.data.user_name
                                profileViewModel.userDescription.value = it.data.user_description
                                profileViewModel.userBestSeller.value = topBook
                                profileViewModel.userBestSellerList.value = topBookImageList
                                profileViewModel.userBestSellerIsbnList.value = topBookIsbnList

                                viewPagerSetting()
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun viewPagerSetting() = with(binding){
        profileAdapter = ProfileAdapter(requireParentFragment())
        vpType.adapter = profileAdapter
        vpType.offscreenPageLimit = 1
        TabLayoutMediator(tlType, vpType){tab, position ->
            when(position){
                0 -> {
                    tab.text = "통계"
                }
                1 -> {
                    tab.text = "활동"
                }
                2 -> {
                    tab.text = "메모"
                }
                3 -> {
                    tab.text = "모임"
                }
            }
        }.attach()
    }

    private fun setColoredText(tvFriend: TextView, relationCount: Int) {
        val text = "친구 ${relationCount}명"
        val spannableString = SpannableString(text)

        val startIndex = text.indexOf(relationCount.toString())
        val endIndex = startIndex + relationCount.toString().length

        spannableString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvFriend.text = spannableString
    }
}