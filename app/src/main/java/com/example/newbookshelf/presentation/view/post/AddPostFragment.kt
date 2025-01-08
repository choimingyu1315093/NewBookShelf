package com.example.newbookshelf.presentation.view.post

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.data.model.post.general.AddPostData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentAddPostBinding
import com.example.newbookshelf.presentation.view.chat.ChatroomFragmentArgs
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.dialog.KakaoSearchDialog
import com.example.newbookshelf.presentation.view.post.dialog.SearchBookDialog
import com.example.newbookshelf.presentation.view.profile.dialog.NicknameChangeDialog
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import java.util.Calendar

class AddPostFragment : Fragment(), KakaoSearchDialog.OnSelectedPlace, SearchBookDialog.OnSelectedBook {
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "AddPostFragment"
    }

    private lateinit var type: String
    private lateinit var kakaoKey: String
    private var isClicked = false

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
        observeViewModel()
    }

    private fun init() = with(binding){
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
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

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etDate.setText("${year}-${month+1}-${dayOfMonth}")
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val amPm = if (hourOfDay < 12) "오전" else "오후"
                val hourFormatted = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                etTime.setText(String.format("%s %02d:%02d", amPm, hourFormatted, minute))
            }

            TimePickerDialog(requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        btnAdd.setOnClickListener {
            isClicked = true
            if(type == "general"){
                if(etTitle.text.toString() == "" || etContent.text.toString() == ""){
                    Toast.makeText(activity, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                }else {
                    val addPostData = AddPostData(etTitle.text.toString().trim(), etContent.text.toString().trim())
                    postViewModel.addPost(addPostData)
                }
            }
        }

        ivBook.setOnClickListener {
            val dialog = SearchBookDialog(this@AddPostFragment)
            dialog.show(requireActivity().supportFragmentManager, "SearchBookDialog")
        }
    }

    private fun observeViewModel() = with(binding){
        postViewModel.addPostResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        if(isClicked){
                            findNavController().popBackStack()
                            isClicked = false
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    //장소 선택
    override fun onSelectedPlace(place: String) = with(binding) {
        etPlace.setText(place)
    }

    //책 선택
    override fun onSelectedBook(book: SearchBookResult) = with(binding) {
        etBook.setText(book.book_name)
    }
}