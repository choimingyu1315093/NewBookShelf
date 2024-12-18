package com.example.newbookshelf.presentation.view.post

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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

        ivBook.setOnClickListener {
            val dialog = SearchBookDialog(this@AddPostFragment)
            dialog.show(requireActivity().supportFragmentManager, "SearchBookDialog")
        }
    }

    override fun onSelectedPlace(place: String) = with(binding) {
        etPlace.setText(place)
    }

    override fun onSelectedBook(book: String) = with(binding) {
        etBook.setText(book)
    }
}