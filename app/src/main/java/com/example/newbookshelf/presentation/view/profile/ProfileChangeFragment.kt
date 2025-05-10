package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.data.model.profile.TopBookData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentProfileChangeBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.home.dialog.BestsellerFilterDialog
import com.example.newbookshelf.presentation.view.post.dialog.SearchBookDialog
import com.example.newbookshelf.presentation.view.profile.adapter.PhotoAdapter
import com.example.newbookshelf.presentation.view.profile.dialog.NicknameChangeDialog
import com.example.newbookshelf.presentation.view.profile.dialog.OneLineMessageChangeDialog
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel
import java.io.File

class ProfileChangeFragment : Fragment(), NicknameChangeDialog.OnClickListener, OneLineMessageChangeDialog.OnClickListener, SearchBookDialog.OnSelectedBook, PhotoAdapter.OnClickItem {
    private lateinit var binding: FragmentProfileChangeBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "ProfileChangeFragment"
    }

    private lateinit var photoAdapter: PhotoAdapter
    private var bookImageArray = ArrayList<String>()
    private var bookIsbnArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileChangeBinding.bind(view)

        (activity as HomeActivity).binding.cl.visibility = View.GONE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        profileViewModel = (activity as HomeActivity).profileViewModel
        bookImageArray = profileViewModel.userBestSellerList.value!!
        bookIsbnArray = profileViewModel.userBestSellerIsbnList.value!!

        photoAdapter = PhotoAdapter(bookImageArray, requireContext(), this@ProfileChangeFragment, 2)
        rvTopBook.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = photoAdapter
        }
        profileSetting()
    }

    private fun bindViews() = with(binding){
        ivNickname.setOnClickListener {
            val dialog = NicknameChangeDialog(this@ProfileChangeFragment)
            dialog.show(requireActivity().supportFragmentManager, "NicknameChangeDialog")
        }

        ivDescription.setOnClickListener {
            val dialog = OneLineMessageChangeDialog(this@ProfileChangeFragment)
            dialog.show(requireActivity().supportFragmentManager, "OneLineMessageChangeDialog")
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.topBookChangeResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    photoAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun profileSetting() = with(binding){
        tvNickname.text = profileViewModel.userName.value
        tvDescription.text = if(profileViewModel.userDescription.value == "") "한 줄 메시지를 입력해주세요." else profileViewModel.userDescription.value
    }

    override fun nicknameClick(b: Boolean) {
        profileSetting()
    }

    override fun descriptionClick(b: Boolean) {
        profileSetting()
    }

    override fun onSelectedBook(book: SearchBookResult) {
        val newBookImage = book.book_image!!
        val newBookIsbn = book.book_isbn!!

        if (bookImageArray.size < 4) {
            bookImageArray.add(newBookImage)
            bookIsbnArray.add(newBookIsbn)
        } else {
            for (i in 1 until bookImageArray.size) {
                bookImageArray[i - 1] = bookImageArray[i]
                bookIsbnArray[i - 1] = bookIsbnArray[i]
            }
            bookImageArray[bookImageArray.size - 1] = newBookImage
            bookIsbnArray[bookIsbnArray.size - 1] = newBookIsbn
        }

        val topBookData = TopBookData(
            bookIsbnArray.getOrNull(1) ?: "",
            bookIsbnArray.getOrNull(2) ?: "",
            bookIsbnArray.getOrNull(3) ?: ""
        )

        profileViewModel.topBookChange(topBookData)
    }

    override fun selectBook(isPhoto: Boolean) {
        val dialog = SearchBookDialog(this@ProfileChangeFragment, "profile")
        dialog.show(requireActivity().supportFragmentManager, "SearchBookDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}