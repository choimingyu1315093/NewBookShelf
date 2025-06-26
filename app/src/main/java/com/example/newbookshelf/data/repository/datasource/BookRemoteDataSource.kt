package com.example.newbookshelf.data.repository.datasource

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
import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookData
import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookModel
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
import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassData
import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassDetailModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassFinishData
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinData
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassMembersModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassModel
import com.example.newbookshelf.data.model.profile.ActivityModel
import com.example.newbookshelf.data.model.profile.MemoModel
import com.example.newbookshelf.data.model.profile.MyBookModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.model.profile.ReadingStatisticsModel
import com.example.newbookshelf.data.model.profile.TopBookData
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
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookRemoteDataSource {

    suspend fun login(loginData: LoginData): Response<LoginModel>
    suspend fun updateLocation(updateLocationData: UpdateLocationData): Response<OnlyResultModel>
    suspend fun snsLogin(snsLoginData: SnsLoginData): Response<LoginModel>
    suspend fun findId(findIdData: FindIdData): Response<FindModel>
    suspend fun findPw(findPwData: FindPwData): Response<FindModel>
    suspend fun signup(signupData: SignupData): Response<SignupModel>
    suspend fun snsSignup(snsSignupData: SnsSignupData): Response<SignupModel>
    suspend fun idCheck(id: String): Response<CheckModel>
    suspend fun emailCheck(emailCheckData: EmailCheckData): Response<CheckModel>
    suspend fun nicknameCheck(nickname: String): Response<CheckModel>
    suspend fun buyTicket(accessToken: String, ticketData: TicketData): Response<TicketModel>
    fun chatStatus(accessToken: String): Flow<Response<StatusModel>>
    fun alarmCount(accessToken: String): Flow<Response<AlarmCountModel>>
    fun alarmStatus(accessToken: String): Flow<Response<StatusModel>>
    suspend fun alarmList(): Response<AlarmListModel>
    suspend fun alarmAllDelete(): Response<OnlyResultModel>
    suspend fun alarmOneDelete(alarmIdx: Int): Response<OnlyResultModel>
    suspend fun searchBook(bookName: String): Response<SearchBookModel>
    suspend fun searchMoreBook(searchMoreBookData: SearchMoreBookData): Response<SearchMoreBookModel>
    suspend fun detailBook(bookIsbn: String): Response<DetailBookModel>
    suspend fun addMyBook(addMyBookData: AddMyBookData): Response<AddMyBookModel>
    suspend fun addBookReview(addBookReviewData: AddBookReviewData): Response<AddBookReviewModel>
    suspend fun updateBookReview(bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Response<UpdateBookReviewModel>
    suspend fun deleteBookReview(bookCommentIdx: Int): Response<DeleteBookReviewModel>
    suspend fun bookMemo(accessToken: String, bookIsbn: String, getType: String): Response<GetBookMemoModel>
    suspend fun addBookMemo(accessToken: String, addBookMemoData: AddBookMemoData): Response<AddBookMemoModel>
    suspend fun updateBookMemo(accessToken: String, memoIdx: Int, updateBookMemoData: UpdateBookMemoData): Response<UpdateBookMemoModel>
    suspend fun deleteBookMemo(accessToken: String, memoIdx: Int): Response<DeleteBookMemoModel>
    suspend fun myProfile(): Response<MyProfileModel>
    fun readingStatistics(userIdx: Int): Flow<Response<ReadingStatisticsModel>>
    fun profileActivity(userIdx: Int): Flow<Response<ActivityModel>>
    fun profileMemo(): Flow<Response<MemoModel>>
    suspend fun nicknameChange(nickname: String): Response<OnlyResultModel>
    suspend fun descriptionChange(description: String): Response<OnlyResultModel>
    suspend fun topBookChange(topBookData: TopBookData): Response<OnlyResultModel>
    suspend fun addPost(addPostData: AddPostData): Response<AddPostModel>
    suspend fun postList(limit: Int, currentPage: Int): Response<PostModel>
    suspend fun postDetail(postIdx: Int): Response<PostDetailModel>
    suspend fun postComment(postCommentData: PostCommentData): Response<PostCommentModel>
    suspend fun postCommentDelete(postCommentIdx: Int): Response<OnlyResultModel>
    suspend fun postDelete(postIdx: Int): Response<OnlyResultModel>
    suspend fun addScrap(addScrapData: AddScrapData): Response<AddScrapModel>
    suspend fun addReadingClass(addReadingClassData: AddReadingClassData): Response<AddReadingClassModel>
    suspend fun readingClassList(searchWord: String, filterType: String, limit: Int, currentPage: Int): Response<ReadingClassModel>
    suspend fun readingClassDetail(readingClassIdx: Int): Response<ReadingClassDetailModel>
    suspend fun readingClassDelete(readingClassIdx: Int): Response<OnlyResultModel>
    suspend fun readingClassMemberList(readingClassIdx: Int, limit: Int, currentPage: Int): Response<ReadingClassMembersModel>
    suspend fun readingClassJoin(readingClassJoinData: ReadingClassJoinData): Response<ReadingClassJoinModel>
    suspend fun readingClassFinish(readingClassFinishData: ReadingClassFinishData): Response<OnlyResultModel>
    fun wishBookHaveUser(): Flow<Response<WishBookHaveUserModel>>
    suspend fun createChatroom(accessToken: String, createChatroomData: CreateChatroomData): Response<ChatroomModel>
    suspend fun deleteChatroom(accessToken: String, chatroomIdx: Int): Response<DeleteChatroomModel>
    fun myBookList(readType: String): Flow<Response<MyBookModel>>
    suspend fun userSetting(): Response<UserSettingModel>
    suspend fun updateUserSetting(updateUserSettingData: UpdateUserSettingData): Response<OnlyResultModel>
    fun ticketLog(): Flow<Response<TicketLogModel>>
    suspend fun passwordChange(passwordChangeData: PasswordChangeData): Response<OnlyResultModel>
    suspend fun userDelete(): Response<OnlyResultModel>
}