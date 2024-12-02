package com.example.newbookshelf

import android.app.Application
import com.example.newbookshelf.data.util.PreferenceUtil
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookShelfApp: Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        prefs = PreferenceUtil(applicationContext)

        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET,"책꽂이")
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}