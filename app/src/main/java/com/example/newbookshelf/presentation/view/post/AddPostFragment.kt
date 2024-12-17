package com.example.newbookshelf.presentation.view.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentAddPostBinding
import com.example.newbookshelf.presentation.view.chat.ChatroomFragmentArgs
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.dialog.KakaoSearchDialog
import com.example.newbookshelf.presentation.view.profile.dialog.NicknameChangeDialog
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel

class AddPostFragment : Fragment(), KakaoSearchDialog.OnSelectedPlace {
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "AddPostFragment"
    }

    private lateinit var type: String
    private lateinit var kakaoKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPostBinding.bind(view)

        val args: AddPostFragmentArgs by navArgs()
        type = args.type

        init()
        bindViews()
    }

    private fun init() = with(binding){
        kakaoKey = BuildConfig.KAKAO_REST_API_KEY
        postViewModel = (activity as HomeActivity).postViewModel
        if(type == "general"){
            clReading.visibility = View.GONE
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        ivPlace.setOnClickListener {
            val dialog = KakaoSearchDialog(this@AddPostFragment)
            dialog.show(requireActivity().supportFragmentManager, "KakaoSearchDialog")
        }
    }

    override fun onSelectedPlace(place: String) = with(binding) {
        etPlace.setText(place)
    }
}