package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
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

    private fun enqueueMockResponseJson(
        fileName: String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    //id 체크
    @Test
    fun idCheckTest() = runBlocking {
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

    //email 체크
    @Test
    fun emailCheckTest() = runBlocking {
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

    //nickname 체크
    @Test
    fun nicknameCheckTest() = runBlocking {
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

    //알람 갯수
    @Test
    fun alarmCountTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": 0
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzM1NzAxMiwiZXhwIjoxNzMzNDQzNDEyfQ.Eh2wFDW5od_Inn6BGKNgTcpLzQ-U6I8Hyoww7keU6YE"
        val response = service.alarmCount(accessToken)
        val request = server.takeRequest()

        Truth.assertThat(request.getHeader("Authorization")).isEqualTo(accessToken)

        Truth.assertThat(request.path).isEqualTo("/alarms/count")

        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isTrue()
        Truth.assertThat(response.body()?.data).isEqualTo(0)
    }

    //책 검색
    @Test
    fun searchBookTest() = runBlocking {
        enqueueMockResponseJson("searchbook.json")

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzM3MzE5OCwiZXhwIjoxNzMzNDU5NTk4fQ.SSzTJeOGVEB6-nPBffDadgD-WWVSyzCShivk-98OxVw"
        val responseBody = service.searchBook(accessToken, "돈의 속성")
        val request = server.takeRequest()

        Truth.assertThat(request.getHeader("Authorization")).isEqualTo(accessToken)
        Truth.assertThat(request.path).isEqualTo("/books/list?book_name=%EB%8F%88%EC%9D%98%20%EC%86%8D%EC%84%B1")
        Truth.assertThat(responseBody.body()).isNotNull()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}