package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.model.find.FindPwData
import com.example.newbookshelf.data.model.home.notify.AlarmCountModel
import com.example.newbookshelf.data.model.home.searchbook.SearchBookModel
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketModel
import com.example.newbookshelf.data.model.signup.CheckModel
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.model.signup.SignupModel
import com.example.newbookshelf.data.model.signup.SnsSignupData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //로그인
    @POST("authentications/sign-in")
    suspend fun login(
        @Body loginData: LoginData
    ): Response<LoginModel>

    //sns로그인
    @POST("authentications/sign-in")
    suspend fun snsLogin(
        @Body snsLoginData: SnsLoginData
    ): Response<LoginModel>

    //아이디 찾기
    @POST("authentications/find/id")
    suspend fun findId(
        @Body findIdData: FindIdData
    ): Response<FindModel>

    //비밀번호 찾기
    @POST("authentications/find/password")
    suspend fun findPw(
        @Body findPwData: FindPwData
    ): Response<FindModel>

    //회원가입
    @POST("authentications/sign-up")
    suspend fun signup(
        @Body signupData: SignupData
    ): Response<SignupModel>

    //sns회원가입
    @POST("authentications/sign-up")
    suspend fun snsSignup(
        @Body snsSignupData: SnsSignupData
    ): Response<SignupModel>

    //아이디 중복 체크
    @POST("authentications/duplicate/id/{user_id}")
    suspend fun idCheck(
        @Path("user_id") userId: String
    ): Response<CheckModel>

    //이메일 중복 체크
    @POST("authentications/duplicate/email")
    suspend fun emailCheck(
        @Body emailCheckData: EmailCheckData
    ): Response<CheckModel>

    //닉네임 중복 체크
    @POST("authentications/duplicate/name/{user_name}")
    suspend fun nicknameCheck(
        @Path("user_name") userName: String
    ): Response<CheckModel>

    //티켓 구매
    @PATCH("tickets/buy")
    suspend fun buyTickets(
        @Header("Authorization") accessToken: String,
        @Body ticketData: TicketData
    ): Response<TicketModel>

    //알람 갯수
    @GET("alarms/count")
    suspend fun alarmCount(
        @Header("Authorization") accessToken: String,
    ): Response<AlarmCountModel>

    //책 검색
    @GET("books/list")
    suspend fun searchBook(
        @Header("Authorization") accessToken: String,
        @Query("book_name") bookName: String
    ): Response<SearchBookModel>
}
