package com.example.newbookshelf.presentation.di

import android.util.Log
import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.util.CustomCookieJar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @DefaultRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .cookieJar(CustomCookieJar())
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
//                OkHttpClient.Builder()
//                    .addInterceptor { chain ->
//                        val request = chain.request()
//
//                        // 요청 헤더 확인
//                        request.headers.forEach { header ->
//                            Log.d("Request Header", "${header.first}: ${header.second}")
//                        }
//
//                        val response = chain.proceed(request)
//
//                        // 응답 헤더 확인
//                        response.headers.forEach { header ->
//                            Log.d("Response Header", "${header.first}: ${header.second}")
//                        }
//
//                        response
//                    }
//                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @DefaultRetrofit
    @Singleton
    @Provides
    fun provideApiService(@DefaultRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}