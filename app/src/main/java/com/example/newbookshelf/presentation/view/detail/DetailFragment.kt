package com.example.newbookshelf.presentation.view.detail

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookData
import com.example.newbookshelf.data.model.home.bestseller.Item
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentDetailBinding
import com.example.newbookshelf.presentation.view.detail.adapter.DetailAdapter
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.round

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val TAG = "DetailFragment"
    }

    private var book: Item? = null
    private var searchBook: SearchBookResult? = null

    private var bookIsbn = ""
    private var myBookIdx: Int? = 0
    private var readType = "none"
    private var isHaveBook = "n"
    private var readingStartDate = ""
    private var readStartDate = ""
    private var readEndDate = ""
    private var readPage = 0
    private var totalPage = 0
    private var percent = 0

    private var buttonClicked = false

    private lateinit var detailAdapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.cl.visibility = View.GONE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val args: DetailFragmentArgs by navArgs()
        book = args.book
        searchBook = args.searchBook

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        detailViewModel = (activity as HomeActivity).detailViewModel

        setCurrentDate()

        if(book != null){
            detailViewModel.detailBook(book?.isbn!!)
            bookIsbn = book?.isbn!!
        }else {
            detailViewModel.detailBook(searchBook?.book_isbn!!)
            bookIsbn = searchBook?.book_isbn!!
        }

        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                findNavController().popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        detailAdapter = DetailAdapter(requireParentFragment(), bookIsbn)
        vpType.adapter = detailAdapter
        TabLayoutMediator(tlType, vpType){tab, position ->
            when(position){
                0 -> {
                    tab.text = "후기"
                }
                1 -> {
                    tab.text = "메모"
                }
            }
        }.attach()

        clMain.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etPage.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        etStart.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                readingStartDate = s.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etStartDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                readStartDate = s.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etEndDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                readEndDate = s.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != ""){
                    readPage = if(s.toString().toInt() > totalPage) totalPage else s.toString().toInt()
                    tvPage.text = "$readPage/${totalPage}"
                    if(readPage == 0 || readPage.toString() == ""){
                        tvPercent.text = "0%"
                        progress.progress = 0
                    }else {
                        val percent = (readPage.toDouble()/totalPage.toDouble())*100
                        val roundedPercent = round(percent).toInt()
                        tvPercent.text = "${roundedPercent}%"
                        progress.progress = roundedPercent
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        swHave.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isHaveBook = "y"
            }else {
                isHaveBook = "n"
                readType = "none"
            }
        }

        btnWishBook.setOnClickListener {
            btnWishBook.setBackgroundResource(R.drawable.btn_main_no_10)
            btnReadingBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            btnReadBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            clReading.visibility = View.GONE
            clRead.visibility = View.GONE
            readType = "wish"
            readingStartDate = ""
            readStartDate = ""
            readEndDate = ""
            setCurrentDate()
        }

        btnReadingBook.setOnClickListener {
            btnWishBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            btnReadingBook.setBackgroundResource(R.drawable.btn_main_no_10)
            btnReadBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            clReading.visibility = View.VISIBLE
            clRead.visibility = View.GONE
            readType = "reading"
            readStartDate = ""
            readEndDate = ""
            setCurrentDate()
        }

        btnReadBook.setOnClickListener {
            btnWishBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            btnReadingBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            btnReadBook.setBackgroundResource(R.drawable.btn_main_no_10)
            clReading.visibility = View.GONE
            clRead.visibility = View.VISIBLE
            readType = "read"
            readingStartDate = ""
            setCurrentDate()
        }

        etStart.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etStart.setText("${year}-${month+1}-${dayOfMonth}")
                readingStartDate = etStart.text.toString()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        etStartDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etStartDate.setText("${year}-${month+1}-${dayOfMonth}")
                readStartDate = etStartDate.text.toString()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        etEndDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etEndDate.setText("${year}-${month+1}-${dayOfMonth}")
                readEndDate = etEndDate.text.toString()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnSave.setOnClickListener {
//            if (isHaveBook == "n") {
//                val addMyBookData = AddMyBookData(totalPage, bookIsbn, isHaveBook, null, readPage, null, readType)
//                detailViewModel.addMyBook(accessToken, addMyBookData)
//            } else {
//                when (readType) {
//                    "wish" -> {
//                        val addMyBookData = AddMyBookData(totalPage, bookIsbn, isHaveBook, null, readPage, null, readType)
//                        detailViewModel.addMyBook(accessToken, addMyBookData)
//                    }
//
//                    "reading" -> {
//                        readingStartDate = etStart.text.toString()
//                        val addMyBookData = AddMyBookData(totalPage, bookIsbn, isHaveBook, null, readPage, readingStartDate, readType)
//                        detailViewModel.addMyBook(accessToken, addMyBookData)
//                    }
//
//                    "read" -> {
//                        readStartDate = etStartDate.text.toString()
//                        readEndDate = etEndDate.text.toString()
//                        val addMyBookData = AddMyBookData(totalPage, bookIsbn, isHaveBook, readEndDate, readPage, readStartDate, readType)
//                        detailViewModel.addMyBook(accessToken, addMyBookData)
//                    }
//
//                    else -> {
//                        val addMyBookData = AddMyBookData(totalPage, bookIsbn, isHaveBook, null, readPage, null, readType)
//                        detailViewModel.addMyBook(accessToken, addMyBookData)
//                    }
//                }
//            }
            buttonClicked = true
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.detailBookResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        myBookIdx = it.data.my_book_idx
                        readType = it.data.read_type
                        if(it.data.book_image == ""){
                            Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
                        }else {
                            Glide.with(ivBook).load(it.data.book_image).into(ivBook)
                        }

                        if(it.data.book_full_page == 0){
                            clPage.visibility = View.GONE
                        }else {
                            clPage.visibility = View.VISIBLE
                            etPage.setText(it.data.read_page.toString())
                            totalPage = it.data.book_full_page
                            readPage = it.data.read_page
                            if(it.data.read_page == 0){
                                tvPage.text = "0/${totalPage}"
                                tvPercent.text = "0%"
                                progress.progress = 0
                            }else {
                                tvPage.text = "${readPage}/${totalPage}"
                                percent = readPage/totalPage
                                tvPercent.text = "${percent}%"
                                progress.progress = percent
                            }
                        }

                        tvTitle.text = it.data.book_name
                        tvAuthor.text = "${it.data.book_author}"
                        tvPublisher.text = "${it.data.book_publisher}"
                        if(it.data.book_translator == ""){
                            txtTranslator.visibility = View.GONE
                            tvTranslator.visibility = View.GONE
                        }else {
                            txtTranslator.visibility = View.VISIBLE
                            tvTranslator.visibility = View.VISIBLE
                            tvTranslator.text = it.data.book_translator
                        }
                        tvDescription.text = it.data.book_content
                        tvDescription.post{
                            val lineCount = tvDescription.layout.lineCount
                            if (lineCount > 0) {
                                if (tvDescription.layout.getEllipsisCount(lineCount - 1) > 0) {
                                    // 더보기 표시
                                    txtMore.visibility = View.VISIBLE

                                    // 더보기 클릭 이벤트
                                    txtMore.setOnClickListener {
                                        tvDescription.maxLines = Int.MAX_VALUE
                                        txtMore.visibility = View.GONE
                                    }
                                }
                            }
                        }

                        swHave.isChecked = it.data.is_have_book == "y"
                        when(it.data.read_type){
                            "wish" -> {
                                btnWishBook.setBackgroundResource(R.drawable.btn_main_no_10)
                                btnReadingBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                                btnReadBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                                clReading.visibility = View.GONE
                                clRead.visibility = View.GONE
                                readType = "wish"
                                readingStartDate = ""
                                readStartDate = ""
                                readEndDate = ""
                            }
                            "read" -> {
                                btnWishBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                                btnReadingBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                                btnReadBook.setBackgroundResource(R.drawable.btn_main_no_10)
                                clReading.visibility = View.GONE
                                clRead.visibility = View.VISIBLE
                                readType = "read"
                                readingStartDate = ""
                            }
                            "reading" -> {
                                btnWishBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                                btnReadingBook.setBackgroundResource(R.drawable.btn_main_no_10)
                                btnReadBook.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                                clReading.visibility = View.VISIBLE
                                clRead.visibility = View.GONE
                                readType = "reading"
                                readStartDate = ""
                                readEndDate = ""
                            }
                        }

                        if(it.data.read_start_date != "none"){
                            etStart.setText(it.data.read_start_date.split(" ")[0])
                            etStartDate.setText(it.data.read_start_date.split(" ")[0])
                        }
                        if(it.data.read_end_date != "none"){
                            etEndDate.setText(it.data.read_end_date.split(" ")[0])
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }

            detailViewModel.addMyBookResult.observe(viewLifecycleOwner) { response ->
                if(buttonClicked){
                    when (response) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show()
                            buttonClicked = false
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
            }
        }
    }

    private fun setCurrentDate() = with(binding){
        val dt = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now = sdf.format(dt).toString()
        etStart.setText(now)
        etStartDate.setText(now)
        etEndDate.setText(now)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}