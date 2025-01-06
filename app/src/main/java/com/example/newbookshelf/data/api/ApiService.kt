package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.chat.ChatroomModel
import com.example.newbookshelf.data.model.chat.CreateChatroomData
import com.example.newbookshelf.data.model.chat.DeleteChatroomModel
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
import com.example.newbookshelf.data.model.home.notify.StatusModel
import com.example.newbookshelf.data.model.home.searchbook.SearchBookModel
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.model.map.WishBookHaveUserModel
import com.example.newbookshelf.data.model.post.AddScrapData
import com.example.newbookshelf.data.model.post.AddScrapModel
import com.example.newbookshelf.data.model.post.general.AddPostData
import com.example.newbookshelf.data.model.post.general.AddPostModel
import com.example.newbookshelf.data.model.post.general.PostCommentData
import com.example.newbookshelf.data.model.post.general.PostCommentModel
import com.example.newbookshelf.data.model.post.general.PostDetailModel
import com.example.newbookshelf.data.model.post.general.PostModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.model.profile.ActivityModel
import com.example.newbookshelf.data.model.profile.MemoModel
import com.example.newbookshelf.data.model.profile.MyBookModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.model.setting.PasswordChangeData
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketLogModel
import com.example.newbookshelf.data.model.setting.TicketModel
import com.example.newbookshelf.data.model.setting.UpdateUserSettingData
import com.example.newbookshelf.data.model.setting.UserSettingModel
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

    //현재 위치 업데이트
    @PATCH("users/location")
    suspend fun updateLocation(
        @Body updateLocationData: UpdateLocationData
    ): Response<OnlyResultModel>

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

    //채팅 여부
    @GET("chat-rooms/status")
    suspend fun chatStatus(
        @Header("Authorization") accessToken: String,
    ): Response<StatusModel>

    //알람 갯수
    @GET("alarms/count")
    suspend fun alarmCount(
        @Header("Authorization") accessToken: String,
    ): Response<AlarmCountModel>

    //알람 여부
    @GET("alarms/status")
    suspend fun alarmStatus(
        @Header("Authorization") accessToken: String,
    ): Response<StatusModel>

    //알람 리스트
    @GET("alarms/list")
    suspend fun alarmList(
    ): Response<AlarmListModel>

    //알람 전체 삭제
    @DELETE("alarms/all")
    suspend fun alarmAllDelete(
    ): Response<OnlyResultModel>

    //알람 개별 삭제
    @DELETE("alarms/{alarm_idx}")
    suspend fun alarmOneDelete(
        @Path("alarm_idx") alarmIdx: Int
    ): Response<OnlyResultModel>

    //책 검색
    @GET("books/list")
    suspend fun searchBook(
        @Query("book_name") bookName: String
    ): Response<SearchBookModel>

    //책 상세 검색
    @GET("books/{book_isbn}")
    suspend fun detailBook(
        @Path("book_isbn") bookIsbn: String
    ): Response<DetailBookModel>

    //내 책 등록
    @POST("my-books")
    suspend fun addMyBook(
        @Body addMyBookData: AddMyBookData
    ): Response<AddMyBookModel>

    //책 평가 등록
    @POST("book-comments")
    suspend fun addReview(
        @Body addBookReviewData: AddBookReviewData
    ): Response<AddBookReviewModel>

    //책 평가 수정
    @PUT("book-comments/{book_comment_idx}")
    suspend fun updateReview(
        @Path("book_comment_idx") bookCommentIdx: Int,
        @Body updateBookReviewData: UpdateBookReviewData
    ): Response<UpdateBookReviewModel>

    //책 평가 삭제
    @DELETE("book-comments/{book_comment_idx}")
    suspend fun deleteReview(
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

    //내 프로필 조회
    @GET("users/profile")
    suspend fun myProfile(
    ): Response<MyProfileModel>

    //활동 조회
    @GET("activities/list")
    suspend fun activitiesList(
        @Query("user_idx") userIdx: Int
    ): Response<ActivityModel>

    //내가 작성한 메모 목록 조회
    @GET("memos/list")
    suspend fun memosList(
    ): Response<MemoModel>

    //닉네임 변경
    @PATCH("users/name/{user_name}")
    suspend fun nicknameChange(
        @Path("user_name") userName: String
    ): Response<OnlyResultModel>

    //한 줄 메시지 변경
    @PATCH("users/description/{user_description}")
    suspend fun descriptionChange(
        @Path("user_description") userDescription: String
    ): Response<OnlyResultModel>

    //게시글 등록
    @POST("posts")
    suspend fun addPost(
        @Body addPostData: AddPostData
    ): Response<AddPostModel>

    //게시글 목록 조회
    @GET("posts/list")
    suspend fun postList(
        @Query("user_idx") userIdx: Int,
        @Query("limit") limit: Int,
        @Query("current_page") currentPage: Int
    ): Response<PostModel>

    //게시글 상세 조회
    @GET("posts/{post_idx}")
    suspend fun postDetail(
        @Path("post_idx") postIdx: Int
    ): Response<PostDetailModel>

    //게시글 댓글 등록
    @POST("post-comments")
    suspend fun postComment(
        @Body postCommentData: PostCommentData
    ): Response<PostCommentModel>

    //게시글 댓글 삭제
    @DELETE("post-comments/{post_comment_idx}")
    suspend fun postCommentDelete(
        @Path("post_comment_idx") postCommentIdx: Int
    ): Response<OnlyResultModel>

    //게시글 삭제
    @DELETE("posts/{post_idx}")
    suspend fun postDelete(
        @Path("post_idx") postIdx: Int
    ): Response<OnlyResultModel>

    //스크랩
    @POST("scraps")
    suspend fun addScrap(
        @Body addScrapData: AddScrapData
    ): Response<AddScrapModel>

    //내가 읽고 싶은 책을 보유 중인 유저 목록 조회
    @GET("users/list/wish-book")
    suspend fun wishBookHaveUser(
    ): Response<WishBookHaveUserModel>

    //채팅방 생성
    @POST("chat-rooms")
    suspend fun createChatroom(
        @Header("Authorization") accessToken: String,
        @Body createChatroomData: CreateChatroomData
    ): Response<ChatroomModel>

    //채팅방 나가기
    @DELETE("chat-rooms/{chat_room_idx}")
    suspend fun deleteChatroom(
        @Header("Authorization") accessToken: String,
        @Path("chat_room_idx") chatRoomIdx: Int
    ): Response<DeleteChatroomModel>

    //내 책 목록 조회
    @GET("my-books/list")
    suspend fun myBookList(
        @Query("read_type") readType: String
    ): Response<MyBookModel>

    //설정 관련 유저 정보 조회
    @GET("users/setting")
    suspend fun userSetting(
    ): Response<UserSettingModel>

    //설정 업데이트
    @PATCH("users/setting")
    suspend fun updateUserSetting(
        @Body updateUserSettingData: UpdateUserSettingData
    ): Response<OnlyResultModel>

    //구매 내역 조회
    @GET("ticket-logs")
    suspend fun ticketLog(
    ): Response<TicketLogModel>

    //비밀번호 변경
    @PATCH("authentications/change/password")
    suspend fun passwordChange(
        @Body passwordChangeData: PasswordChangeData
    ): Response<OnlyResultModel>

    //회원 탈퇴
    @DELETE("authentications")
    suspend fun userDelete(
    ): Response<OnlyResultModel>
}
