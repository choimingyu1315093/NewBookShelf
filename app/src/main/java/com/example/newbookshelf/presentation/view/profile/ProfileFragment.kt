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

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "ProfileFragment"
    }

    private lateinit var accessToken: String
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        (activity as HomeActivity).binding.cl.visibility = View.GONE

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        profileViewModel = (activity as HomeActivity).profileViewModel

        profileAdapter = ProfileAdapter(requireParentFragment())
        vpType.adapter = profileAdapter
        vpType.offscreenPageLimit = 1
        TabLayoutMediator(tlType, vpType){tab, position ->
            when(position){
                0 -> {
                    tab.text = "활동"
                }
                1 -> {
                    tab.text = "메모"
                }
                2 -> {
                    tab.text = "모임"
                }
            }
        }.attach()
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
        profileViewModel.myProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
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

                        profileViewModel.userName.value = it.data.user_name
                        profileViewModel.userDescription.value = it.data.user_description
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