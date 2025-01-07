package com.example.newbookshelf.presentation.view.post

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.DateFormat
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentReadingDetailBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import java.util.Locale

class ReadingDetailFragment : Fragment() {
    private lateinit var binding: FragmentReadingDetailBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "ReadingDetailFragment"
    }

    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)
    private var readingClassIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reading_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReadingDetailBinding.bind(view)

        val args: ReadingDetailFragmentArgs by navArgs()
        readingClassIdx = args.readingClassIdx

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        postViewModel = (activity as HomeActivity).postViewModel
        postViewModel.readingClassDetail(readingClassIdx)
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        postViewModel.readingClassDetailResult.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        val readingClass = response.data.data
                        tvTitle.text = readingClass.post_title
                        tvName.text = readingClass.user_name
                        tvTime.text = readingClass.update_date.split("T")[0]
                        tvContent.text = readingClass.post_content
                        tvSchedule.text = DateFormat.convertToCustomFormat(readingClass.club_meet_date)

                        if(readingClass.book_image == ""){
                            Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
                        }else {
                            Glide.with(ivBook).load(readingClass.book_image).into(ivBook)
                        }

                        Log.d(TAG, "observeViewModel: ${getAddressFromLatLng(requireContext(), 37.4220936, -122.083922)}")

                        tvBookTitle.text = readingClass.book_name
                        tvAuthor.text = readingClass.book_author
                        tvPublisher.text = readingClass.book_publisher

                        if(readingClass.book_translator == ""){
                            txtTranslator.visibility = View.GONE
                        }else {
                            txtTranslator.visibility = View.VISIBLE
                            tvTranslator.text = readingClass.book_translator
                        }
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

    fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            // 위도와 경도를 사용해 주소 리스트 가져오기
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                // 첫 번째 주소 반환
                addresses[0].getAddressLine(0) // 전체 주소
            } else {
                null // 주소를 찾을 수 없는 경우
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // 에러 발생 시 null 반환
        }
    }
}