package com.example.newbookshelf.presentation.view.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.data.model.post.AddScrapData
import com.example.newbookshelf.data.model.post.general.PostCommentData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentGeneralDetailBinding
import com.example.newbookshelf.presentation.view.detail.DetailFragmentArgs
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.post.adapter.GeneralAdapter
import com.example.newbookshelf.presentation.view.post.adapter.GeneralDetailReviewAdapter
import com.example.newbookshelf.presentation.view.post.dialog.ReviewDeleteDialog
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel

class GeneralDetailFragment : Fragment(), ReviewDeleteDialog.OnDeleteClickListener {
    private lateinit var binding: FragmentGeneralDetailBinding
    private lateinit var postViewModel: PostViewModel

    companion object {
        const val TAG = "GeneralDetailFragment"
    }

    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)
    private var writerIdx = 0
    private var postIdx = 0
    private var isScrap = false
    private lateinit var generalDetailReviewAdapter: GeneralDetailReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_general_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneralDetailBinding.bind(view)

        val args: GeneralDetailFragmentArgs by navArgs()
        postIdx = args.postIdx

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        postViewModel = (activity as HomeActivity).postViewModel
        postViewModel.postDetail(postIdx)
        generalDetailReviewAdapter = (activity as HomeActivity).generalDetailReviewAdapter
        generalDetailReviewAdapter.setOnDeleteListener {
            postViewModel.postCommentDelete(it.post_comment_idx)
        }
        rvReview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = generalDetailReviewAdapter
        }

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etReview.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        ivMore.setOnClickListener {
            showPopupMenu(it)
        }

        etReview.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                postComment(etReview.text.toString().trim())
                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(etReview.windowToken, 0)
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun observeViewModel() = with(binding){
        postViewModel.postDetailResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        val post = response.data.data
                        writerIdx = post.users.user_idx
                        tvTitle.text = post.post_title
                        tvContent.text = post.post_content
                        tvName.text = post.users.user_name
                        tvTime.text = post.update_date.split("T")[0]
                        tvReview.text = "댓글 (${post.post_comments.size})"
                        if(post.post_comments.isNotEmpty()){
                            rvReview.visibility = View.VISIBLE
                            txtEmpty.visibility = View.GONE
                            generalDetailReviewAdapter.differ.submitList(post.post_comments)
                        }else {
                            rvReview.visibility = View.GONE
                            txtEmpty.visibility = View.VISIBLE
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

        postViewModel.postCommentResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        postViewModel.postDetail(postIdx)
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }

        postViewModel.postCommentDeleteResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(response.data!!.result){
                        postViewModel.postDetail(postIdx)
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
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

        postViewModel.postDeleteResult.observe(viewLifecycleOwner){ response ->
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
    }

    //댓글 등록
    private fun postComment(content: String){
        val postCommentData = PostCommentData(postIdx, content)
        postViewModel.postComment(postCommentData)
        binding.etReview.setText("")
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
                    val addScrapData = AddScrapData("POST", postIdx)
                    postViewModel.addScrap(addScrapData)
                    isScrap = true
                    true
                }
                R.id.delete -> {
                    val dialog = ReviewDeleteDialog(this)
                    dialog.show(requireActivity().supportFragmentManager, "ReviewDeleteDialog")
                    isScrap = true
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    //게시글 삭제
    override fun delete(b: Boolean) {
        postViewModel.postDelete(postIdx)
    }
}