package com.example.newbookshelf.presentation.view.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentAgreeBinding
import com.example.newbookshelf.presentation.view.login.LoginActivity
import com.example.newbookshelf.presentation.view.signup.dialog.WebViewDialog

class AgreeFragment : Fragment() {
    private lateinit var binding: FragmentAgreeBinding

    companion object {
        const val TAG = "AgreePageFragment"
    }

    private var isAllChecked: Boolean = false
    private var isCb1Checked: Boolean = false
    private var isCb2Checked: Boolean = false
    private var isCb3Checked: Boolean = false
    private var isCb4Checked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agree, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAgreeBinding.bind(view)

        bindViews()
    }

    private fun bindViews() = with(binding){
        cbAll.setOnCheckedChangeListener { _, isChecked ->
            isAllChecked = isChecked
            if (isAllChecked) {
                setAllBoxes(true)
            } else {
                if (isCb1Checked && isCb2Checked && isCb3Checked && isCb4Checked) {
                    setAllBoxes(false)
                }
            }
        }

        cb1.setOnCheckedChangeListener { _, isChecked ->
            isCb1Checked = isChecked
            checkBoxes()
        }

        cb2.setOnCheckedChangeListener { _, isChecked ->
            isCb2Checked = isChecked
            checkBoxes()
        }

        cb3.setOnCheckedChangeListener { _, isChecked ->
            isCb3Checked = isChecked
            checkBoxes()
        }

        cb4.setOnCheckedChangeListener { _, isChecked ->
            isCb4Checked = isChecked
            checkBoxes()
        }

        ivCb1.setOnClickListener {
            val link = "https://velog.io/@jongwoong0401/%EC%B1%85%EA%BD%82%EC%9D%B4-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80"
            val dialog = WebViewDialog(link)
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light)
            dialog.show(requireActivity().supportFragmentManager, "WebViewDialog")
        }

        ivCb2.setOnClickListener {
            val link = "https://velog.io/@jongwoong0401/%EC%B1%85%EA%BD%82%EC%9D%B4-%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8"
            val dialog = WebViewDialog(link)
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light)
            dialog.show(requireActivity().supportFragmentManager, "WebViewDialog")
        }

        ivCb3.setOnClickListener {
            val link = "https://velog.io/@jongwoong0401/%EC%B1%85%EA%BD%82%EC%9D%B4-%EC%9C%84%EC%B9%98%EA%B8%B0%EB%B0%98%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80"
            val dialog = WebViewDialog(link)
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light)
            dialog.show(requireActivity().supportFragmentManager, "WebViewDialog")
        }

        ivCb4.setOnClickListener {
            val link = "https://naver.com"
            val dialog = WebViewDialog(link)
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light)
            dialog.show(requireActivity().supportFragmentManager, "WebViewDialog")
        }

        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_agreeFragment_to_inputFragment)
        }
    }

    private fun checkBoxes() = with(binding){
        cbAll.isChecked = isCb1Checked && isCb2Checked && isCb3Checked && isCb4Checked
        if(isCb1Checked && isCb2Checked){
            btnNext.isEnabled = true
            btnNext.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.btn_main_no_10))
        }else {
            btnNext.isEnabled = false
            btnNext.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.btn_e9e9e9_10))
        }
    }

    private fun setAllBoxes(b: Boolean) = with(binding) {
        cb1.isChecked = b
        cb2.isChecked = b
        cb3.isChecked = b
        cb4.isChecked = b
        cbAll.isChecked = b
        checkNextButtonEnable()
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(cbAll.isChecked){
            btnNext.isEnabled = true
            btnNext.setBackgroundResource(R.drawable.btn_main_no_10)
        }else {
            btnNext.isEnabled = false
            btnNext.setBackgroundResource(R.drawable.btn_e9e9e9_10)
        }
    }
}