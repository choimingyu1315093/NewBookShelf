package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookData
import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookModel
import com.example.newbookshelf.data.model.detail.detail.DetailBookModel
import com.example.newbookshelf.data.model.detail.memo.AddBookMemoData
import com.example.newbookshelf.data.model.detail.memo.AddBookMemoModel
import com.example.newbookshelf.data.model.detail.memo.DeleteBookMemoModel
import com.example.newbookshelf.data.model.detail.memo.GetBookMemoModel
import com.example.newbookshelf.data.model.detail.memo.UpdateBookMemoData
import com.example.newbookshelf.data.model.detail.memo.UpdateBookMemoModel
import com.example.newbookshelf.data.model.detail.review.AddBookReviewData
import com.example.newbookshelf.data.model.detail.review.AddBookReviewModel
import com.example.newbookshelf.data.model.detail.review.DeleteBookReviewModel
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewData
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewModel
import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.model.find.FindPwData
import com.example.newbookshelf.data.model.home.notify.AlarmCountModel
import com.example.newbookshelf.data.model.home.notify.AlarmListModel
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
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
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

    //알람 리스트
    @GET("alarms/list")
    suspend fun alarmList(
        @Header("Authorization") accessToken: String,
    ): Response<AlarmListModel>

    //알람 전체 삭제
    @DELETE("alarms/all")
    suspend fun alarmAllDelete(
        @Header("Authorization") accessToken: String,
    ): Response<OnlyResultModel>

    //알람 개별 삭제
    @DELETE("alarms/{alarm_idx}")
    suspend fun alarmOneDelete(
        @Header("Authorization") accessToken: String,
        @Path("alarm_idx") alarmIdx: Int
    ): Response<OnlyResultModel>

    //책 검색
    @GET("books/list")
    suspend fun searchBook(
        @Header("Authorization") accessToken: String,
        @Query("book_name") bookName: String
    ): Response<SearchBookModel>

    //책 상세 검색
    @GET("books/{book_isbn}")
    suspend fun detailBook(
        @Header("Authorization") accessToken: String,
        @Path("book_isbn") bookIsbn: String
    ): Response<DetailBookModel>

    //내 책 등록
    @POST("my-books")
    suspend fun addMyBook(
        @Header("Authorization") accessToken: String,
        @Body addMyBookData: AddMyBookData
    ): Response<AddMyBookModel>

    //책 평가 등록
    @POST("book-comments")
    suspend fun addReview(
        @Header("Authorization") accessToken: String,
        @Body addBookReviewData: AddBookReviewData
    ): Response<AddBookReviewModel>

    //책 평가 수정
    @PUT("book-comments/{book_comment_idx}")
    suspend fun updateReview(
        @Header("Authorization") accessToken: String,
        @Path("book_comment_idx") bookCommentIdx: Int,
        @Body updateBookReviewData: UpdateBookReviewData
    ): Response<UpdateBookReviewModel>

    //책 평가 삭제
    @DELETE("book-comments/{book_comment_idx}")
    suspend fun deleteReview(
        @Header("Authorization") accessToken: String,
        @Path("book_comment_idx") bookCommentIdx: Int,
    ): Response<DeleteBookReviewModel>

    //해당 책의 메모 조회
    @GET("memos/{book_isbn}")
    suspend fun bookMemo(
        @Header("Authorization") accessToken: String,
        @Query("book_isbn") bookIsbn: String,
        @Query("get_type") getType: String
    ): Response<GetBookMemoModel>

    //메모 등록
    @POST("memos")
    suspend fun addBookMemo(
        @Header("Authorization") accessToken: String,
        @Body addBookMemoData: AddBookMemoData
    ): Response<AddBookMemoModel>

    //메모 수정
    @PATCH("memos/{memo_idx}")
    suspend fun updateMemo(
        @Header("Authorization") accessToken: String,
        @Path("memo_idx") memoIdx: Int,
        @Body updateBookMemoData: UpdateBookMemoData
    ): Response<UpdateBookMemoModel>

    //메모 삭제
    @DELETE("memos/{memo_idx}")
    suspend fun deleteMemo(
        @Header("Authorization") accessToken: String,
        @Path("memo_idx") memoIdx: Int,
    ): Response<DeleteBookMemoModel>
}
