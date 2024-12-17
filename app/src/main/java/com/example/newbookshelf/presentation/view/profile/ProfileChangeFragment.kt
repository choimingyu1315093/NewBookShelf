package com.example.newbookshelf.presentation.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentProfileChangeBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.profile.dialog.NicknameChangeDialog
import com.example.newbookshelf.presentation.view.profile.dialog.OneLineMessageChangeDialog
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel

class ProfileChangeFragment : Fragment(), NicknameChangeDialog.OnClickListener, OneLineMessageChangeDialog.OnClickListener {
    private lateinit var binding: FragmentProfileChangeBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "ProfileChangeFragment"
    }

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
    }

    private fun init() = with(binding){
        profileViewModel = (activity as HomeActivity).profileViewModel
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

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}