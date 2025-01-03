package com.example.newbookshelf.domain.repository

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
import com.example.newbookshelf.data.model.home.searchbook.SearchBookModel
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.model.map.WishBookHaveUserModel
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
import com.example.newbookshelf.data.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookRepository {

    suspend fun login(loginData: LoginData): Resource<LoginModel>
    suspend fun updateLocation(updateLocationData: UpdateLocationData): Resource<OnlyResultModel>
    suspend fun snsLogin(snsLoginData: SnsLoginData): Resource<LoginModel>
    suspend fun findId(findIdData: FindIdData): Resource<FindModel>
    suspend fun findPw(findPwData: FindPwData): Resource<FindModel>
    suspend fun signup(signupData: SignupData): Resource<SignupModel>
    suspend fun snsSignup(snsSignupData: SnsSignupData): Resource<SignupModel>
    suspend fun idCheck(id: String): Resource<CheckModel>
    suspend fun emailCheck(emailCheckData: EmailCheckData): Resource<CheckModel>
    suspend fun nicknameCheck(nickname: String): Resource<CheckModel>
    suspend fun buyTicket(accessToken: String, ticketData: TicketData): Resource<TicketModel>
    fun chatStatus(accessToken: String): Flow<Resource<OnlyResultModel>>
    fun alarmCount(accessToken: String): Flow<Resource<AlarmCountModel>>
    fun alarmStatus(accessToken: String): Flow<Resource<OnlyResultModel>>
    fun alarmList(accessToken: String): Flow<Resource<AlarmListModel>>
    suspend fun alarmAllDelete(accessToken: String): Resource<OnlyResultModel>
    suspend fun alarmOneDelete(accessToken: String, alarmIdx: Int): Resource<OnlyResultModel>
    suspend fun searchBook(bookName: String): Resource<SearchBookModel>
    fun searchedBook(): Flow<List<SearchedBook>>
    suspend fun insert(searchedBook: SearchedBook)
    suspend fun delete(searchedBook: SearchedBook)
    suspend fun allDelete()
    suspend fun detailBook(bookIsbn: String): Resource<DetailBookModel>
    suspend fun addMyBook(addMyBookData: AddMyBookData): Resource<AddMyBookModel>
    suspend fun addBookReview(addBookReviewData: AddBookReviewData): Resource<AddBookReviewModel>
    suspend fun updateBookReview(bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Resource<UpdateBookReviewModel>
    suspend fun deleteBookReview(bookCommentIdx: Int): Resource<DeleteBookReviewModel>
    suspend fun bookMemo(accessToken: String, bookIsbn: String, getType: String): Resource<GetBookMemoModel>
    suspend fun addBookMemo(accessToken: String, addBookMemoData: AddBookMemoData): Resource<AddBookMemoModel>
    suspend fun updateBookMemo(accessToken: String, bookMemoIdx: Int, updateBookMemoData: UpdateBookMemoData): Resource<UpdateBookMemoModel>
    suspend fun deleteBookMemo(accessToken: String, bookMemoIdx: Int): Resource<DeleteBookMemoModel>
    fun myProfile(): Flow<Resource<MyProfileModel>>
    fun profileActivity(accessToken: String, userIdx: Int): Flow<Resource<ActivityModel>>
    fun profileMemo(accessToken: String): Flow<Resource<MemoModel>>
    suspend fun nicknameChange(accessToken: String, nickname: String): Resource<OnlyResultModel>
    suspend fun descriptionChange(accessToken: String, description: String): Resource<OnlyResultModel>
    fun wishBookHaveUser(accessToken: String): Flow<Resource<WishBookHaveUserModel>>
    suspend fun createChatroom(accessToken: String, createChatroomData: CreateChatroomData): Resource<ChatroomModel>
    suspend fun deleteChatroom(accessToken: String, chatroomIdx: Int): Resource<DeleteChatroomModel>
    fun myBookList(accessToken: String, readType: String): Flow<Resource<MyBookModel>>
    fun userSetting(accessToken: String): Flow<Resource<UserSettingModel>>
    suspend fun updateUserSetting(accessToken: String, updateUserSettingData: UpdateUserSettingData): Resource<OnlyResultModel>
    fun ticketLog(accessToken: String): Flow<Resource<TicketLogModel>>
    suspend fun passwordChange(accessToken: String, passwordChangeData: PasswordChangeData): Resource<OnlyResultModel>
    suspend fun userDelete(accessToken: String): Resource<OnlyResultModel>
}