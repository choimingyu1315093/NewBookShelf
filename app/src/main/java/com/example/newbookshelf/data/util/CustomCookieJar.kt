package com.example.newbookshelf.data.util

import okhttp3.HttpUrl

class CustomCookieJar : okhttp3.CookieJar {
    private val cookieStore = mutableMapOf<String, List<okhttp3.Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<okhttp3.Cookie>) {
        cookieStore[url.host] = cookies // URL의 호스트를 기준으로 쿠키 저장
    }

    override fun loadForRequest(url: HttpUrl): List<okhttp3.Cookie> {
        return cookieStore[url.host] ?: emptyList() // 요청에 필요한 쿠키 반환
    }
}