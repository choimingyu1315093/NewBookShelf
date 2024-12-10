package com.example.newbookshelf.data.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", android.content.Context.MODE_PRIVATE)

    //fcmToken
    fun getFcmToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setFcmToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //AccessToken
    fun getAccessToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setAccessToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //UserIdx
    fun getUserIdx(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }
    fun setUserIdx(key: String, defValue: Int){
        prefs.edit().putInt(key, defValue).apply()
    }

    //AutoLogin
    fun getAutoLogin(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
    fun setAutoLogin(key: String, defValue: Boolean){
        prefs.edit().putBoolean(key, defValue).apply()
    }

    //loginType
    fun getLoginType(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setLoginType(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //loginId
    fun getLoginId(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setLoginId(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //loginPassword
    fun getLoginPw(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setLoginPw(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //KakaoToken
    fun getKakaoToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setKakaoToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //GoogleToken
    fun getGoogleToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setGoogleToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //NaverToken
    fun getNaverToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setNaverToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //Nickname
    fun getNickname(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setNickname(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }
}