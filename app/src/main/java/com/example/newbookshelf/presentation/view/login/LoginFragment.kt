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
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentLoginBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModel
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

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    companion object {
        const val TAG = "LoginFragment"
        const val GOOGLE_LOGIN = 100
    }

    private lateinit var fcmToken: String
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

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etId.windowToken, 0)
        }
    }

    private fun googleLogin() = with(binding){
        ivGoogle.setOnClickListener {
            auth = (activity as LoginActivity).firebaseAuth

            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            if(account == null){
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_LOGIN)
            }else {
                val snsLoginData = SnsLoginData(fcmToken, "google", account.idToken!!)
                loginViewModel.snsLogin(snsLoginData)
            }
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
//                            MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
//                            if(MyApplication.prefs.getNaverToken("naverToken","")  == ""){
//                                Log.d(TAG, "bindViews: 네이버 회원가입 후 로그인")
//                                MyApplication.prefs.setNaverToken("naverToken", NaverIdLoginSDK.getAccessToken()!!)
//                                val snsSignUpModel = SnsSignUpModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "naver", email, MyApplication.prefs.getNaverToken("naverToken", ""), nickname)
//                                Log.d(TAG, "onSuccess: snsSignUpModel $snsSignUpModel")
//                                loginViewModel.snsSignUp(snsSignUpModel)
//                            }else {
//                                Log.d(TAG, "bindViews: 네이버 로그인")
//                                val snsSignInModel = SnsSignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "naver", MyApplication.prefs.getNaverToken("naverToken", ""))
//                                Log.d(TAG, "onSuccess: snsSignInModel $snsSignInModel")
//                                loginViewModel.snsSignIn(snsSignInModel)
//                            }
//                            MyApplication.prefs.setLoginType("loginType", "naver")
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
                            Log.d(TAG, "kakaoLogin: $authorizationCode")
//                            MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
//                            if(MyApplication.prefs.getKakaoToken("kakaoToken", "") == ""){
//                                Log.d(TAG, "bindViews: 카카오 회원가입 후 로그인")
//                                MyApplication.prefs.setKakaoToken("kakaoToken", authorizationCode)
//                                val snsSignUpModel = SnsSignUpModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "kakao", email, MyApplication.prefs.getKakaoToken("kakaoToken", ""), nickname)
//                                Log.d(TAG, "bindViews: snsSignUpModel $snsSignUpModel")
//                                loginViewModel.snsSignUp(snsSignUpModel)
//                            }else {
//                                Log.d(TAG, "bindViews: 카카오 로그인")
//                                val snsSignInModel = SnsSignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "kakao", MyApplication.prefs.getKakaoToken("kakaoToken", ""))
//                                Log.d(TAG, "bindViews: snsSignInModel $snsSignInModel")
//                                loginViewModel.snsSignIn(snsSignInModel)
//                            }
//                            MyApplication.prefs.setLoginType("loginType", "kakao")
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

        btnLogin.setOnClickListener {
            val loginData = LoginData(fcmToken, "general", id, password)
            loginViewModel.login(loginData)
        }
    }

    private fun observeViewModels() = with(binding){
        loginViewModel.fcmToken.observe(viewLifecycleOwner){ token ->
            fcmToken = token
        }
        
        loginViewModel.loginResult.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    if(response.data!!.result){
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
                Log.d(TAG, "onActivityResult: account ${account.idToken}, nickname $nickname, email $email")

                //처음 로그인하면 account.idToken값 null 된다.
//                MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
//                if(MyApplication.prefs.getGoogleToken("googleToken","")  == ""){
//                    Log.d(TAG, "bindViews: 구글 회원가입 후 로그인")
//                    MyApplication.prefs.setGoogleToken("googleToken", account.idToken!!)
//                    val snsSignUpModel = SnsSignUpModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "google", email, MyApplication.prefs.getGoogleToken("googleToken", ""), nickname)
//                    loginViewModel.snsSignUp(snsSignUpModel)
//                }else {
//                    Log.d(TAG, "bindViews: 구글 로그인")
    //                    val snsSignInModel = SnsSignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "google", MyApplication.prefs.getGoogleToken("googleToken", ""))
    //                    loginViewModel.snsSignIn(snsSignInModel)
//                }
//                MyApplication.prefs.setLoginType("loginType", "google")
            } catch (e: ApiException) {
                Log.d(TAG, "Google sign in failed", e)
            }
        }
    }
}