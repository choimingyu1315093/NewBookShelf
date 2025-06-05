package com.example.newbookshelf.presentation.view.profile.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentNicknameChangeDialogBinding
import com.example.newbookshelf.domain.repository.BookRepository
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel

class NicknameChangeDialog(private val onClickListener: OnClickListener) : DialogFragment() {
    private lateinit var binding: FragmentNicknameChangeDialogBinding
    private lateinit var profileViewModel: ProfileViewModel

    interface OnClickListener {
        fun nicknameClick(b: Boolean)
    }

    companion object {
        const val TAG = "NicknameChangeDialog"
    }

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
        return inflater.inflate(R.layout.fragment_nickname_change_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNicknameChangeDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        profileViewModel = (activity as HomeActivity).profileViewModel

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etNickname.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == ""){
                    btnOk.isEnabled = false
                    btnOk.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
                    tvCount.visibility = View.GONE
                    ivDelete.visibility = View.GONE
                }else {
                    btnOk.isEnabled = true
                    btnOk.setBackgroundResource(R.drawable.btn_main_no_10)
                    tvCount.visibility = View.VISIBLE
                    tvCount.text = "${s.toString().length}/20"
                    ivDelete.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        ivDelete.setOnClickListener {
            etNickname.setText("")
        }

        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            isClicked = true
            profileViewModel.nicknameChange(etNickname.text.toString())
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.nicknameChangeResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(isClicked){
                        dismiss()
                        txtNicknameWarning.visibility = View.GONE
                        profileViewModel.userName.value = etNickname.text.toString()
                        onClickListener.nicknameClick(true)
                        isClicked = false
                    }
                }
                is Resource.Error -> {
                    txtNicknameWarning.visibility = View.VISIBLE
                }
                is Resource.Loading -> Unit
                else -> Unit
            }
        }
    }
}