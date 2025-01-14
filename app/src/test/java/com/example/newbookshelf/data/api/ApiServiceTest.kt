package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.chat.CreateChatroomData
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
import java.security.cert.TrustAnchor

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

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsImlhdCI6MTczNTg3ODU4MiwiZXhwIjoxNzM1ODg1NzgyfQ.CcB7kUoDAUZ8_AXUEr_jZRU7sjn2pOMFvCtVtUGqZOw"
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

        val responseBody = service.searchBook("돈의 속성")
        val request = server.takeRequest()

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

        val response = service.detailBook("1188331795")
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

        val addMyBookData = AddMyBookData(216, "K662930932", "n", null, 0, null, "none")
        val response = service.addMyBook(addMyBookData)
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

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsImlhdCI6MTczNTg4Njc5NywiZXhwIjoxNzM1ODkzOTk3fQ.pyl4xKXhQOs8lC2rinAefGt14SGtcNAUcRSUa0gFcPo"
        val response = service.bookMemo(accessToken,"K662930932", "all")
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

        val response = service.myProfile()
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/users/profile")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.data!!.user_grade).isEqualTo("씨앗 4")
    }

    //활동 조회
    @Test
    fun profileActivityTest() = runBlocking {
        enqueueMockResponseJson("profileactivity.json")

        val response = service.activitiesList(33)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/activities/list?user_idx=33")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.data!![0].activity_point).isEqualTo(2)
    }

    //내가 작성한 메모 목록 조회
    @Test
    fun profileMemoTest() = runBlocking {
        enqueueMockResponseJson("profilememo.json")

        val response = service.memosList()
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/memos/list")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()?.data!![0].memo_content).isEqualTo("gooooood")
    }

    //내가 읽고 싶은 책을 보유 중인 유저 목록 조회
    @Test
    fun wishBookHaveUserTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": [
                {
                  "my_book_idx": 11,
                  "book_name": "알라딘 상품정보 - 소년이 온다",
                  "book_author": "한강 지음",
                  "book_translator": "",
                  "book_publisher": "창비",
                  "book_image": "https://image.aladin.co.kr/product/4086/97/coversum/8936434128_2.jpg",
                  "book_isbn": "K662930932",
                  "user_idx": 35,
                  "user_name": "test002",
                  "user_point": 3,
                  "current_latitude": "37.5121136000000",
                  "current_longitude": "127.1207262000000",
                  "distance": 866
                }
              ]
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val response = service.wishBookHaveUser()
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/users/list/wish-book")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()!!.data[0].book_image).isEqualTo("https://image.aladin.co.kr/product/4086/97/coversum/8936434128_2.jpg")
    }

    //채팅방 생성
    @Test
    fun createChatroomTest() = runBlocking {
        enqueueMockResponseJson("createchatroom.json")

        val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MzMsInVzZXJfaWQiOiJ0ZXN0MDAxIiwidXNlcl90eXBlIjoidXNlciIsImlhdCI6MTczNDA1MDc2NSwiZXhwIjoxNzM0MTM3MTY1fQ.fGCm8H6SvGpRy9_SZU2ivYOR5Hv948ZJ7Npxsb53WjA"
        val createChatroomData = CreateChatroomData("K662930932", 35)
        val response = service.createChatroom(accessToken, createChatroomData)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/chat-rooms")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()!!.data.chat_room_idx).isEqualTo(1)
    }

    //내 책 목록 조회
    @Test
    fun myBookListTest() = runBlocking {
        enqueueMockResponseJson("mybooklist.json")

       val response = service.myBookList("read")
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/my-books/list?read_type=read")
        Truth.assertThat(response.body()).isNotNull()
        Truth.assertThat(response.body()!!.data[0].my_book_idx).isEqualTo(12)
    }

    //설정 관련 유저 정보 조회
    @Test
    fun userSettingTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": {
                "user_idx": 33,
                "user_name": "test001",
                "ticket_count": 1,
                "setting_chat_alarm": 1,
                "setting_marketing_alarm": 1,
                "setting_wish_book_alarm": 1,
                "setting_chat_receive": 1
              }
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val response = service.userSetting()
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/users/setting")
        Truth.assertThat(response.body()!!.data.user_idx).isEqualTo(33)
    }

    //일반 게시글 조회
    @Test
    fun postListTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": [
                {
                  "post_idx": 2,
                  "post_title": "첫 번째 게시글",
                  "create_date": "2025-01-06T13:17:47.203Z",
                  "post_comment_count": 0
                }
              ],
              "pagination": {
                "total": 1,
                "current_page": 1,
                "limit": 10,
                "block": 10,
                "current_block": 1,
                "total_page": 1,
                "total_block": 1
              }
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val response = service.postList(10, 1)
        val request = server.takeRequest()

        Truth.assertThat(request.path).isEqualTo("/posts/list?user_idx=33&limit=10&current_page=1")
        Truth.assertThat(response.body()!!.data[0].post_idx).isEqualTo(2)
    }

    //일반 게시글 상세 조회
    @Test
    fun postDetailTest() = runBlocking {
        enqueueMockResponseJson("postdetail.json")

        val response = service.postDetail(2)
        val request = server.takeRequest()

        Truth.assertThat(response.body()!!.data.post_title).isEqualTo("첫 번째 게시글")
    }

    //독서모임글 조회
    @Test
    fun readingClassListText() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": [
                {
                  "club_post_idx": 1,
                  "post_title": "string",
                  "club_latitude": "0.0000000000000",
                  "club_longitude": "0.0000000000000",
                  "club_meet_date": "2024-12-31T12:56:21.000Z",
                  "book_name": "영원한 천국",
                  "book_image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6706295%3Ftimestamp%3D20240824161450"
                }
              ],
              "pagination": {
                "total": 1,
                "current_page": 1,
                "limit": 10,
                "block": 10,
                "current_block": 1,
                "total_page": 1,
                "total_block": 1
              }
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val response = service.readingClassList("", "latest", 10, 1)
        Truth.assertThat(response.body()!!.data[0].post_title).isEqualTo("string")
    }

    //독서모임글 상세 조회
    @Test
    fun readingClassDetailTest() = runBlocking {
        val mockResponseBody = """
            {
              "result": true,
              "data": {
                "club_post_idx": 1,
                "user_idx": 36,
                "user_name": "",
                "create_date": "2024-12-31T12:56:24.775Z",
                "update_date": "2024-12-31T12:56:24.775Z",
                "post_title": "string",
                "post_content": "string",
                "club_latitude": "0.0000000000000",
                "club_longitude": "0.0000000000000",
                "club_meet_date": "2024-12-31T12:56:21.000Z",
                "club_result_image": "",
                "book_isbn": "1167374568",
                "book_name": "영원한 천국",
                "book_author": "정유정",
                "book_translator": "",
                "book_publisher": "은행나무",
                "book_image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6706295%3Ftimestamp%3D20240824161450",
                "user_type": "viewer",
                "is_scrap": false
              }
            }
        """.trimIndent()
        enqueueMockResponse(mockResponseBody)

        val response = service.readingClassDetail(1)
        Truth.assertThat(response.body()!!.data.book_name).isEqualTo("영원한 천국")
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}