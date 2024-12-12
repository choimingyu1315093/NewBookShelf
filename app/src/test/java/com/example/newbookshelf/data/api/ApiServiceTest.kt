package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookData
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

    //책 상세 검색
    @Test
    fun detailBookTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": {
                "book_isbn": "1188331795",
                "book_name": "돈의 속성(300쇄 리커버에디션)",
                "book_content": "2020ㆍ2021ㆍ2022ㆍ2023 4년 연속 최장기 베스트셀러 80만 깨어있는 독자들이 선택한 경제경영 필독서 『돈의 속성』  ▶ 『돈의 속성』 300쇄 기념 개정증보판 발행! ▶ 『돈의 속성』 300쇄 기념, 김승호 회장의 추가 메시지를 담다! ▶ 중국, 일본, 대만, 태국 4개국 출간! 이 책은 초판 발행 후, 경제경영 필도서로 자리매김한 『돈의 속성』 300쇄 기념 개정증보판이다. 300쇄에 맞춰 코로나19로 바뀐 경제상황과 돈에 관한 김승호",
                "book_author": "김승호",
                "book_translator": "",
                "book_publisher": "스노우폭스북스",
                "book_image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5385029%3Ftimestamp%3D20241129143044",
                "book_full_page": 0,
                "is_have_book": false,
                "read_type": "none",
                "read_start_date": "none",
                "read_end_date": "none",
                "read_page": 0,
                "edit_full_page": true
              }
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzcyNjQ0OSwiZXhwIjoxNzMzODEyODQ5fQ.3XTdQtRL14fjfihSw83dWpZcmSvbOM9iqaRKqsGnC5w"
        val response = service.detailBook(accessToken, "1188331795")
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/books/1188331795")

        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isTrue()
        Truth.assertThat(response.body()?.data?.book_author).isEqualTo("김승호")
    }

    //내 책 등록
    @Test
    fun addMyBookTest() = runBlocking {
        enqueueMockResponseJson("addmybook.json")

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzcyNjQ0OSwiZXhwIjoxNzMzODEyODQ5fQ.3XTdQtRL14fjfihSw83dWpZcmSvbOM9iqaRKqsGnC5w"
        val addMyBookData = AddMyBookData(216, "K662930932", "n", null, 0, null, "none")
        val response = service.addMyBook(accessToken, addMyBookData)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/my-books")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isTrue()
        Truth.assertThat(response.body()?.data!!.books.book_name).isEqualTo("알라딘 상품정보 - 소년이 온다")
    }

    //해당 책의 메모 조회
    @Test
    fun bookMemoTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": [
                {
                  "memo_idx": 1,
                  "memo_content": "너무 좋은 책이에요",
                  "is_public": "y",
                  "create_date": "2024-12-09T23:57:35.736Z",
                  "users": {
                    "user_idx": 33,
                    "user_name": "test001"
                  }
                }
              ]
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzc4ODUwOSwiZXhwIjoxNzMzODc0OTA5fQ.xL9ra7qdTQPw7-ckmnflOMY_AZ2bIP8lDT4m5PMDFTU"
        val response = service.bookMemo(accessToken, "K662930932", "all")
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/memos/%7Bbook_isbn%7D?book_isbn=K662930932&get_type=all")

        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.result).isTrue()
        Truth.assertThat(response.body()?.data!![0].memo_content).isEqualTo("너무 좋은 책이에요")
    }

    //내 프로필 조회
    @Test
    fun myProfileTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": {
                "user_idx": 33,
                "user_name": "test001",
                "user_description": "",
                "user_point": 98,
                "user_grade": "씨앗 4",
                "user_max_point": 100,
                "relation_count": 0,
                "relation_request_count": 0
              }
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzk3Njk0OSwiZXhwIjoxNzM0MDYzMzQ5fQ.SZn2PF_rNAiB0Kh6WFmClqQJSYwgKmK6t-SCPoDtoH8"
        val response = service.myProfile(accessToken)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/users/profile")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.data!!.user_grade).isEqualTo("씨앗 4")
    }

    //활동 조회
    @Test
    fun profileActivityTest() = runBlocking {
        enqueueMockResponseJson("profileactivity.json")
        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzk3OTg1MiwiZXhwIjoxNzM0MDY2MjUyfQ.S-pmNP_eRJYucpw4W72lzFb0COih5a01HenUVHvHkH0"
        val response = service.activitiesList(accessToken, 33)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/activities/list?user_idx=33")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.data!![0].activity_point).isEqualTo(2)
    }

    //내가 작성한 메모 목록 조회
    @Test
    fun profileMemoTest() = runBlocking {
        enqueueMockResponseJson("profilememo.json")
        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczMzk3OTg1MiwiZXhwIjoxNzM0MDY2MjUyfQ.S-pmNP_eRJYucpw4W72lzFb0COih5a01HenUVHvHkH0"
        val response = service.memosList(accessToken)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/memos/list")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.data!![0].memo_content).isEqualTo("gooooood")
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}