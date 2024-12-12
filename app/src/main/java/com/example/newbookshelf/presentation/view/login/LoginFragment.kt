package com.example.newbookshelf.presentation.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.model.signup.SnsSignupData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentLoginBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.signup.SignUpActivity
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModel
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlin.math.log
import kotlin.math.sign

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var signupViewModel: SignupViewModel

    companion object {
        const val TAG = "LoginFragment"
        const val GOOGLE_LOGIN = 100
    }

    private var fcmToken = ""
    private var id = ""
    private var password = ""
    private var isIDCheck = false
    private var isPWCheck = false

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        init()
        googleLogin()
        naverLogin()
        kakaoLogin()
        bindViews()
        observeViewModels()
    }

    private fun init() = with(binding){
        loginViewModel = (activity as LoginActivity).loginViewModel
        signupViewModel = (activity as LoginActivity).signupViewModel

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etId.windowToken, 0)
        }

        fcmToken = BookShelfApp.prefs.getFcmToken("fcmToken", "")

        if(BookShelfApp.prefs.getAutoLogin("autoLogin", false)){
            if(BookShelfApp.prefs.getLoginType("loginType", "general") == "general"){
                val loginData = LoginData(fcmToken, "general", BookShelfApp.prefs.getLoginId("id", ""), BookShelfApp.prefs.getLoginPw("password", ""))
                loginViewModel.login(loginData)
            }else if(BookShelfApp.prefs.getLoginType("loginType", "general") == "kakao"){
                val snsLoginData = SnsLoginData(fcmToken, "kakao", BookShelfApp.prefs.getKakaoToken("kakaoToken", ""))
                loginViewModel.snsLogin(snsLoginData)
            }else if(BookShelfApp.prefs.getLoginType("loginType", "general") == "naver"){
                val snsLoginData = SnsLoginData(fcmToken, "naver", BookShelfApp.prefs.getNaverToken("naverToken", ""))
                loginViewModel.snsLogin(snsLoginData)
            }else {
                val snsLoginData = SnsLoginData(fcmToken, "google", BookShelfApp.prefs.getGoogleToken("googleToken",""))
                loginViewModel.snsLogin(snsLoginData)
            }
        }
    }

    private fun googleLogin() = with(binding){
        ivGoogle.setOnClickListener {
            auth = (activity as LoginActivity).firebaseAuth

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_LOGIN)
        }
    }

    private fun naverLogin() = with(binding){
        ivNaver.setOnClickListener {
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(result: NidProfileResponse) {
                            val nickname = result.profile?.nickname
                            val email = result.profile?.email
                            BookShelfApp.prefs.setNickname("nickname", nickname ?: "Nickname")
                            if(BookShelfApp.prefs.getNaverToken("naverToken","")  == ""){
                                Log.d(TAG, "bindViews: 네이버 회원가입 후 로그인")
                                BookShelfApp.prefs.setKakaoToken("naverToken", NaverIdLoginSDK.getAccessToken()!!)
                                val snsSignupData = SnsSignupData(fcmToken, "naver", email, NaverIdLoginSDK.getAccessToken()!!, nickname)
                                signupViewModel.snsSignup(snsSignupData)
                            }else {
                                Log.d(TAG, "bindViews: 네이버 로그인")
                                val snsLoginData = SnsLoginData(fcmToken, "naver", BookShelfApp.prefs.getNaverToken("naverToken", ""))
                                loginViewModel.snsLogin(snsLoginData)
                            }
                            BookShelfApp.prefs.setLoginType("loginType", "naver")
                        }

                        override fun onError(errorCode: Int, message: String) {
                            //
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            //
                        }
                    })
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Toast.makeText(requireContext(), "errorCode: $errorCode, errorDesc: $errorDescription", Toast.LENGTH_SHORT).show()
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }
            NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
        }
    }

    private fun kakaoLogin() = with(binding){
        ivKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "카카오 로그인에 실패 하셨습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val authorizationCode: String = token!!.accessToken
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.d(TAG, "kakaoLogin: 사용자 정보 가져오기 실패")
                        } else if (user != null) {
                            val nickname = user.kakaoAccount?.profile?.nickname
                            val email = user.kakaoAccount?.email
                            BookShelfApp.prefs.setNickname("nickname", nickname ?: "Nickname")
                            if(BookShelfApp.prefs.getKakaoToken("kakaoToken", "") == ""){
                                Log.d(TAG, "bindViews: 카카오 회원가입 후 로그인")
                                BookShelfApp.prefs.setKakaoToken("kakaoToken", authorizationCode)
                                val snsSignupData = SnsSignupData(fcmToken, "google", email, authorizationCode, nickname)
                                signupViewModel.snsSignup(snsSignupData)
                            }else {
                                Log.d(TAG, "bindViews: 카카오 로그인")
                                val snsLoginData = SnsLoginData(fcmToken, "kakao", BookShelfApp.prefs.getKakaoToken("kakaoToken", ""))
                                loginViewModel.snsLogin(snsLoginData)
                            }
                            BookShelfApp.prefs.setLoginType("loginType", "kakao")
                        }
                    }
                }
            }
        }
    }

    private fun bindViews() = with(binding){
        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isIDCheck = s.toString() != ""
                id = s.toString()
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isPWCheck = s.toString() != ""
                password = s.toString()
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        txtFindId.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", "아이디")
            }
            findNavController().navigate(R.id.action_loginFragment_to_findFragment, bundle)
        }

        txtFindPw.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", "비밀번호")
            }
            findNavController().navigate(R.id.action_loginFragment_to_findFragment, bundle)
        }

        txtSignup.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val loginData = LoginData(fcmToken, "general", id, password)
            loginViewModel.login(loginData)
        }
    }

    private fun observeViewModels() = with(binding){
        loginViewModel.loginResult.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    if(response.data!!.result){
                        BookShelfApp.prefs.setAutoLogin("autoLogin", true)
                        BookShelfApp.prefs.setAccessToken("accessToken", response.data.data.accessToken)
                        BookShelfApp.prefs.setUserIdx("userIdx", response.data.data.userIdx)
                        loginViewModel.updateLocation(response.data.data.accessToken, UpdateLocationData(loginViewModel.latitude.value!!, loginViewModel.longitude.value!!))
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        signupViewModel.signupResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    if(response.data!!.result){
                        BookShelfApp.prefs.setAutoLogin("autoLogin", true)
                        BookShelfApp.prefs.setAccessToken("accessToken", response.data.data.access_token)
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
        
        loginViewModel.updateLocationResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(isIDCheck && isPWCheck){
            btnLogin.isEnabled = true
            btnLogin.setBackgroundResource(R.drawable.btn_main_no_10)
        }else {
            btnLogin.isEnabled = false
            btnLogin.setBackgroundResource(R.drawable.btn_e9e9e9_10)
        }
    }
    
    private fun showProgressBar() = with(binding){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() = with(binding){
        progressBar.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val nickname = account.displayName
                val email = account.email

                BookShelfApp.prefs.setNickname("nickname", nickname ?: "Nickname")
                if(BookShelfApp.prefs.getGoogleToken("googleToken","")  == ""){
                    Log.d(TAG, "bindViews: 구글 회원가입 후 로그인")
                    BookShelfApp.prefs.setGoogleToken("googleToken", account.idToken!!)
                    val snsSignupData = SnsSignupData(fcmToken, "google", email, account.idToken!!, nickname)
                    signupViewModel.snsSignup(snsSignupData)
                }else {
                    Log.d(TAG, "bindViews: 구글 로그인")
                    val snsLoginData = SnsLoginData(fcmToken, "google", BookShelfApp.prefs.getGoogleToken("googleToken",""))
                    loginViewModel.snsLogin(snsLoginData)
                }
                BookShelfApp.prefs.setLoginType("loginType", "google")
            } catch (e: ApiException) {
                Log.d(TAG, "Google sign in failed", e)
            }
        }
    }
}