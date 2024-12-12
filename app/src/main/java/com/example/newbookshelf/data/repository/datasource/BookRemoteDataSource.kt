package com.example.newbookshelf.data.repository.datasource

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
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.model.map.WishBookHaveUserModel
import com.example.newbookshelf.data.model.profile.ActivityModel
import com.example.newbookshelf.data.model.profile.MemoModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketModel
import com.example.newbookshelf.data.model.signup.CheckModel
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.model.signup.SignupModel
import com.example.newbookshelf.data.model.signup.SnsSignupData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookRemoteDataSource {

    suspend fun login(loginData: LoginData): Response<LoginModel>
    suspend fun updateLocation(accessToken: String, updateLocationData: UpdateLocationData): Response<OnlyResultModel>
    suspend fun snsLogin(snsLoginData: SnsLoginData): Response<LoginModel>
    suspend fun findId(findIdData: FindIdData): Response<FindModel>
    suspend fun findPw(findPwData: FindPwData): Response<FindModel>
    suspend fun signup(signupData: SignupData): Response<SignupModel>
    suspend fun snsSignup(snsSignupData: SnsSignupData): Response<SignupModel>
    suspend fun idCheck(id: String): Response<CheckModel>
    suspend fun emailCheck(emailCheckData: EmailCheckData): Response<CheckModel>
    suspend fun nicknameCheck(nickname: String): Response<CheckModel>
    suspend fun buyTicket(accessToken: String, ticketData: TicketData): Response<TicketModel>
    fun alarmCount(accessToken: String): Flow<Response<AlarmCountModel>>
    fun alarmList(accessToken: String): Flow<Response<AlarmListModel>>
    suspend fun alarmAllDelete(accessToken: String): Response<OnlyResultModel>
    suspend fun alarmOneDelete(accessToken: String, alarmIdx: Int): Response<OnlyResultModel>
    suspend fun searchBook(accessToken: String, bookName: String): Response<SearchBookModel>
    suspend fun detailBook(accessToken: String, bookIsbn: String): Response<DetailBookModel>
    suspend fun addMyBook(accessToken: String, addMyBookData: AddMyBookData): Response<AddMyBookModel>
    suspend fun addBookReview(accessToken: String, addBookReviewData: AddBookReviewData): Response<AddBookReviewModel>
    suspend fun updateBookReview(accessToken: String, bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Response<UpdateBookReviewModel>
    suspend fun deleteBookReview(accessToken: String, bookCommentIdx: Int): Response<DeleteBookReviewModel>
    suspend fun bookMemo(accessToken: String, bookIsbn: String, getType: String): Response<GetBookMemoModel>
    suspend fun addBookMemo(accessToken: String, addBookMemoData: AddBookMemoData): Response<AddBookMemoModel>
    suspend fun updateBookMemo(accessToken: String, memoIdx: Int, updateBookMemoData: UpdateBookMemoData): Response<UpdateBookMemoModel>
    suspend fun deleteBookMemo(accessToken: String, memoIdx: Int): Response<DeleteBookMemoModel>
    fun myProfile(accessToken: String): Flow<Response<MyProfileModel>>
    fun profileActivity(accessToken: String, userIdx: Int): Flow<Response<ActivityModel>>
    fun profileMemo(accessToken: String): Flow<Response<MemoModel>>
    suspend fun nicknameChange(accessToken: String, nickname: String): Response<OnlyResultModel>
    suspend fun descriptionChange(accessToken: String, description: String): Response<OnlyResultModel>
    fun wishBookHaveUser(accessToken: String): Flow<Response<WishBookHaveUserModel>>
}