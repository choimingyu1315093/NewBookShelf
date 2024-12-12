package com.example.newbookshelf.presentation.view.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.ActivityLoginBinding
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModel
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModel
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var findViewModelFactory: FindViewModelFactory
    @Inject
    lateinit var signupViewModelFactory: SignupViewModelFactory

    lateinit var loginViewModel: LoginViewModel
    lateinit var findViewModel: FindViewModel
    lateinit var signupViewModel: SignupViewModel

    companion object {
        const val TAG = "LoginActivity"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findViewModel = ViewModelProvider(this, findViewModelFactory).get(FindViewModel::class.java)
        signupViewModel = ViewModelProvider(this, signupViewModelFactory).get(SignupViewModel::class.java)

        getFcmToken()
        requestPermission()
    }

    private fun getFcmToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d(LoginFragment.TAG, "login getFcmToken: $token")
                    BookShelfApp.prefs.setFcmToken("fcmToken", token)
                } else {
                    Log.e("FCM Token", "Failed to get token: ${task.exception}")
                }
            }
    }

    private fun requestPermission(){
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    getLastKnownLocation()
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Log.d(TAG, "거부된 권한들: $deniedPermissions")
                    Toast.makeText(application, "필요한 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("필요한 권한을 허용해주세요.")
            .setPermissions(*permissions.toTypedArray())
            .check()
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                Log.d(TAG, "현재 위치: ${location.latitude}, ${location.longitude}")
                loginViewModel.latitude.postValue(location.latitude)
                loginViewModel.longitude.postValue(location.longitude)
                BookShelfApp.prefs.setLatitude("latitude", location.latitude.toFloat())
                BookShelfApp.prefs.setLongitude("longitude", location.longitude.toFloat())
            } else {
                Toast.makeText(this, "위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "위치 가져오기 실패: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}