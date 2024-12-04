package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var service: ApiService
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun enqueueMockResponse(responseBody: String, responseCode: Int = 200) {
        val mockResponse = MockResponse()
            .setResponseCode(responseCode)
            .setBody(responseBody)
        server.enqueue(mockResponse)
    }

    @Test
    fun idCheck() = runBlocking {
        // 준비: Mock 응답 설정
        val mockResponseBody = """
            {
              "result": false,
              "statusCode": 409,
              "message": "이미 사용 중인 아이디입니다."
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val userId = "test001"
        val response = service.idCheck(userId)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/authentications/duplicate/id/$userId")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isFalse()
        Truth.assertThat(response.body()?.message).isEqualTo("이미 사용 중인 아이디입니다.")
    }

    @Test
    fun emailCheck() = runBlocking {
        // 준비: Mock 응답 설정
        val mockResponseBody = """
            {
              "result": false,
              "statusCode": 409,
              "message": "이미 사용 중인 이메일입니다."
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val emailCheckData = EmailCheckData(userEmail = "alsrb1315093@gmail.com")
        val response = service.emailCheck(emailCheckData)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/authentications/duplicate/email")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isFalse()
        Truth.assertThat(response.body()?.message).isEqualTo("이미 사용 중인 이메일입니다.")
    }

    @Test
    fun nicknameCheck() = runBlocking {
        val mockResponseBody = """
            {"result":false,"statusCode":404,"message":"Cannot GET /authentications/duplicate/name/test001"}
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val user_name = "test001"
        val response = service.nicknameCheck(user_name)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/authentications/duplicate/name/$user_name")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isFalse()
        Truth.assertThat(response.body()?.message).isEqualTo("Cannot GET /authentications/duplicate/name/test001")

    }

    @After
    fun tearDown() {
        server.shutdown() // MockWebServer 종료
    }
}