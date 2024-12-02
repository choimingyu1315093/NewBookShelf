package com.example.newbookshelf.data.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", android.content.Context.MODE_PRIVATE)

    //NaverToken
    fun getNaverToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setNaverToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }
}