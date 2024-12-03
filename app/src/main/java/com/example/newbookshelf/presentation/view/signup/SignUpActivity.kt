package com.example.newbookshelf.presentation.view.signup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.ActivitySignUpBinding
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    @Inject
    lateinit var signupViewModelFactory: SignupViewModelFactory
    lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signupViewModel = ViewModelProvider(this, signupViewModelFactory).get(SignupViewModel::class.java)
    }
}