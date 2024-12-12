package com.example.newbookshelf.presentation.view.profile.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentOneLineMessageChangeBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel

class OneLineMessageChangeDialog(private val onClickListener: OnClickListener) : DialogFragment() {
    private lateinit var binding: FragmentOneLineMessageChangeBinding
    private lateinit var profileViewModel: ProfileViewModel

    interface OnClickListener {
        fun descriptionClick(b: Boolean)
    }

    companion object {
        const val TAG = "OneLineMessageChangeDialog"
    }

    private lateinit var accessToken: String
    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one_line_message_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOneLineMessageChangeBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        profileViewModel = (activity as HomeActivity).profileViewModel

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etDescription.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == ""){
                    btnOk.isEnabled = false
                    btnOk.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
                    tvCount.visibility = View.GONE
                }else {
                    btnOk.isEnabled = true
                    btnOk.setBackgroundResource(R.drawable.btn_main_no_10)
                    tvCount.visibility = View.VISIBLE
                    tvCount.text = "${s.toString().length}/200"
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            isClicked = true
            profileViewModel.descriptionChange(accessToken, etDescription.text.toString())
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.descriptionChangeResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(isClicked){
                        dismiss()
                        profileViewModel.userDescription.value = etDescription.text.toString()
                        onClickListener.descriptionClick(true)
                        isClicked = false
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}