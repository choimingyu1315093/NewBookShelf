package com.example.newbookshelf.presentation.view.post

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.provider.Telephony.Mms.Addr
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.data.model.post.general.AddPostData
import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassData
import com.example.newbookshelf.data.util.Address
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentAddPostBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.dialog.KakaoSearchDialog
import com.example.newbookshelf.presentation.view.post.dialog.ReadingClassAddDialog
import com.example.newbookshelf.presentation.view.post.dialog.SearchBookDialog
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.util.Calendar

class AddPostFragment : Fragment(), KakaoSearchDialog.OnSelectedPlace, SearchBookDialog.OnSelectedBook, ReadingClassAddDialog.OnClickListener {
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "AddPostFragment"
    }

    private lateinit var type: String
    private lateinit var kakaoKey: String
    private var bookIsbn = ""
    private var latitude = 0.0
    private var longitude = 0.0
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

        btnPlace.setOnClickListener {
            val dialog = KakaoSearchDialog(this@AddPostFragment)
            dialog.show(requireActivity().supportFragmentManager, "KakaoSearchDialog")
        }

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etDate.setText("${year}-${month+1}-${dayOfMonth}")
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
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnBook.setOnClickListener {
            val dialog = SearchBookDialog(this@AddPostFragment, "post")
            dialog.show(requireActivity().supportFragmentManager, "SearchBookDialog")
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
            }else {
                if(etTitle.text.toString() == "" || etContent.text.toString() == "" ||
                    etPlace.text.toString() == "" || etDate.text.toString() == "" ||
                    etTime.text.toString() == "" || etBook.text.toString() == ""){
                    Toast.makeText(activity, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                }else {
                    val dialog = ReadingClassAddDialog(this@AddPostFragment)
                    dialog.show(requireActivity().supportFragmentManager, "ReadingClassAddDialog")
                }
            }
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
                else -> Unit
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                postViewModel.googleMapLatLngResult.collect { state ->
                    when(state){
                        is Resource.Success -> {
                            if(state.data!!.status != "ZERO_RESULTS"){
                                latitude = state.data.results[0].geometry.location.lat
                                longitude = state.data.results[0].geometry.location.lng
                            }
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> Unit
                        else -> Unit
                    }
                }
            }
        }

        postViewModel.addReadingClassResult.observe(viewLifecycleOwner){ response ->
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
                else -> Unit
            }
        }
    }

    //장소 선택
    override fun onSelectedPlace(place: String, address: String): Unit = with(binding) {
        etPlace.setText(place)
        postViewModel.getCoordinatesForAddress(address, requireContext().getString(R.string.GOOGLE_MAP_KEY))
    }

    //책 선택
    override fun onSelectedBook(book: SearchBookResult) = with(binding) {
        etBook.setText(book.book_name)
        bookIsbn = book.book_isbn!!
    }

    //독서 모임 등록
    override fun addReadingClass(b: Boolean): Unit = with(binding){
        val title = "${etTitle.text.toString()}"
        val content = "${etContent.text.toString()}"
        val amOrPm = etTime.text.toString().split(" ")[0]
        val timePart = etTime.text.toString().split(" ")[1]  // "03:05:12" 형태
        val timeParts = timePart.split(":")

        var hour = timeParts[0].toInt()
        val minute = timeParts[1]
        val second = if (timeParts.size > 2) timeParts[2] else "00"

        if (amOrPm == "오후" && hour != 12) {
            hour += 12
        } else if (amOrPm == "오전" && hour == 12) {
            hour = 0
        }

        val hourStr = String.format("%02d", hour)
        val convertedTime = "$hourStr:$minute:$second"

        val date = "${etDate.text.toString()} $convertedTime"
        val addReadingClassDate = AddReadingClassData(bookIsbn, latitude, longitude, date, content, title)
        postViewModel.addReadingClass(addReadingClassDate)
    }
}