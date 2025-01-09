package com.example.newbookshelf.presentation.view.post

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.post.AddScrapData
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinData
import com.example.newbookshelf.data.util.Address
import com.example.newbookshelf.data.util.DateFormat
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentReadingDetailBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.dialog.ReadingClassDeleteDialog
import com.example.newbookshelf.presentation.view.post.dialog.ReadingClassJoinDialog
import com.example.newbookshelf.presentation.view.post.dialog.ReadingClassMemberDialog
import com.example.newbookshelf.presentation.view.post.dialog.ReviewDeleteDialog
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class ReadingDetailFragment : Fragment(), ReadingClassDeleteDialog.OnDeleteClickListener, ReadingClassJoinDialog.OnJoinClickListener {
    private lateinit var binding: FragmentReadingDetailBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "ReadingDetailFragment"
    }

    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)
    private var writerIdx = 0
    private var isScrap = false
    private var readingClassIdx = 0
    private var userIdxList = arrayListOf<Int>()

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
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        postViewModel = (activity as HomeActivity).postViewModel
        postViewModel.readingClassDetail(readingClassIdx)
        postViewModel.readingClassMemberList(readingClassIdx, 30, 1)
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        ivMore.setOnClickListener {
            showPopupMenu(it)
        }

        btnJoin.setOnClickListener {
            when(btnJoin.text.toString()){
                "참가 신청" -> {
                    val dialog = ReadingClassJoinDialog(this@ReadingDetailFragment)
                    dialog.show(requireActivity().supportFragmentManager, "ReadingClassJoinDialog")
                }
                "참가자 조회" -> {
                    val dialog = ReadingClassMemberDialog(readingClassIdx)
                    dialog.show(requireActivity().supportFragmentManager, "ReadingClassMemberDialog")
                }
                "독서 모임 종료" -> {

                }
            }
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
                        tvSchedule.text = "${Address.getAddressFromLatLng(requireContext(), readingClass.club_latitude.toDouble(), readingClass.club_longitude.toDouble())}\n${DateFormat.convertToCustomFormat(readingClass.club_meet_date)}"
                        writerIdx = readingClass.user_idx

                        if(DateFormat.isDatePast(readingClass.club_meet_date)){
                            tvStatus.text = "진행중"
                            tvStatus.setBackgroundResource(R.drawable.btn_main_no_10)
                        }else {
                            tvStatus.text = "예정"
                            tvStatus.setBackgroundResource(R.drawable.btn_main_no_10)
                        }

                        if(readingClass.book_image == ""){
                            Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
                        }else {
                            Glide.with(ivBook).load(readingClass.book_image).into(ivBook)
                        }

                        tvBookTitle.text = readingClass.book_name
                        tvAuthor.text = readingClass.book_author
                        tvPublisher.text = readingClass.book_publisher

                        if(readingClass.book_translator == ""){
                            txtTranslator.visibility = View.GONE
                        }else {
                            txtTranslator.visibility = View.VISIBLE
                            tvTranslator.text = readingClass.book_translator
                        }

                        if((readingClass.user_type == "leader" && !DateFormat.isDatePast(readingClass.club_meet_date)) || readingClass.user_type == "participant"){
                            btnJoin.text = "참가자 조회"
                        }else if(readingClass.user_type == "leader" && DateFormat.isDatePast(readingClass.club_meet_date)){
                            btnJoin.text = "독서 모임 종료"
                        }else {
                            btnJoin.text = "참가 신청"
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

        postViewModel.addScrapResult.observe(viewLifecycleOwner){ response ->
            if(isScrap){
                when(response){
                    is Resource.Success -> {
                        Toast.makeText(activity, "스크랩 등록", Toast.LENGTH_SHORT).show()
                        isScrap = false
                    }
                    is Resource.Error -> {
                        Toast.makeText(activity, "이미 스크랩에 등록한 글입니다.", Toast.LENGTH_SHORT).show()
                        isScrap = false
                    }
                    is Resource.Loading -> Unit
                }
            }
        }

        postViewModel.readingClassDetailResult.observe(viewLifecycleOwner){ response ->
            if(isScrap){
                when(response){
                    is Resource.Success -> {
                        findNavController().popBackStack()
                        isScrap = false
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                }
            }
        }

        postViewModel.readingClassJoinResult.observe(viewLifecycleOwner){ response ->
            if(isScrap){
                when(response){
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "참가 신청이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        btnJoin.text = "참가자 조회"
                        isScrap = false
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                }
            }
        }
        
        postViewModel.readingClassMemberListResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    for(i in 0 until response.data!!.data.size){
                        userIdxList.add(response.data.data[i].users.user_idx)
                    }
                    Log.d(TAG, "observeViewModel: userIdxList $userIdxList")
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun showPopupMenu(anchor: View) {
        val popupMenu = PopupMenu(anchor.context, anchor)
        if(userIdx == writerIdx){
            popupMenu.menuInflater.inflate(R.menu.general_menu, popupMenu.menu)
        }else {
            popupMenu.menuInflater.inflate(R.menu.not_user_general_menu, popupMenu.menu)
        }

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.scrap -> {
                    val addScrapData = AddScrapData("CLUB", readingClassIdx)
                    postViewModel.addScrap(addScrapData)
                    isScrap = true
                    true
                }
                R.id.delete -> {
                    val dialog = ReadingClassDeleteDialog(this)
                    dialog.show(requireActivity().supportFragmentManager, "ReadingClassDeleteDialog")
                    isScrap = true
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    //독서 모임 삭제
    override fun delete(b: Boolean) {
        postViewModel.readingClassDelete(readingClassIdx)
    }

    //독서 모임 참가
    override fun join(b: Boolean) {
        isScrap = true
        val readingClassJoinData = ReadingClassJoinData(readingClassIdx)
        postViewModel.readingClassJoin(readingClassJoinData)
    }
}