package com.example.newbookshelf.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentSettingBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        init()
    }

    private fun init() = with(binding){
        (activity as HomeActivity).binding.cl.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
    }
}