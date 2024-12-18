package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.api.ApiService
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
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.presentation.di.DefaultRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class BookRemoteDataSourceImpl(@DefaultRetrofit private val apiService: ApiService): BookRemoteDataSource {

    override suspend fun login(loginData: LoginData): Response<LoginModel> {
        return apiService.login(loginData)
    }

    override suspend fun updateLocation(accessToken: String, updateLocationData: UpdateLocationData): Response<OnlyResultModel> {
        return apiService.updateLocation(accessToken, updateLocationData)
    }

    override suspend fun snsLogin(snsLoginData: SnsLoginData): Response<LoginModel> {
        return apiService.snsLogin(snsLoginData)
    }

    override suspend fun findId(findIdData: FindIdData): Response<FindModel> {
        return apiService.findId(findIdData)
    }

    override suspend fun findPw(findPwData: FindPwData): Response<FindModel> {
        return apiService.findPw(findPwData)
    }

    override suspend fun signup(signupData: SignupData): Response<SignupModel> {
        return apiService.signup(signupData)
    }

    override suspend fun snsSignup(snsSignupData: SnsSignupData): Response<SignupModel> {
        return apiService.snsSignup(snsSignupData)
    }

    override suspend fun idCheck(id: String): Response<CheckModel> {
        return apiService.idCheck(id)
    }

    override suspend fun emailCheck(emailCheckData: EmailCheckData): Response<CheckModel> {
        return apiService.emailCheck(emailCheckData)
    }

    override suspend fun nicknameCheck(nickname: String): Response<CheckModel> {
        return apiService.nicknameCheck(nickname)
    }

    override suspend fun buyTicket(accessToken: String, ticketData: TicketData): Response<TicketModel> {
        return apiService.buyTickets(accessToken, ticketData)
    }

    override fun alarmCount(accessToken: String): Flow<Response<AlarmCountModel>> {
        return flow {
            emit(apiService.alarmCount(accessToken))
        }
    }

    override fun alarmList(accessToken: String): Flow<Response<AlarmListModel>> {
        return flow {
            emit(apiService.alarmList(accessToken))
        }
    }

    override suspend fun alarmAllDelete(accessToken: String): Response<OnlyResultModel> {
        return apiService.alarmAllDelete(accessToken)
    }

    override suspend fun alarmOneDelete(accessToken: String, alarmIdx: Int): Response<OnlyResultModel> {
        return apiService.alarmOneDelete(accessToken, alarmIdx)
    }

    override suspend fun searchBook(accessToken: String, bookName: String): Response<SearchBookModel> {
        return apiService.searchBook(accessToken, bookName)
    }

    override suspend fun detailBook(accessToken: String, bookIsbn: String): Response<DetailBookModel> {
        return apiService.detailBook(accessToken, bookIsbn)
    }

    override suspend fun addMyBook(accessToken: String, addMyBookData: AddMyBookData): Response<AddMyBookModel> {
        return apiService.addMyBook(accessToken, addMyBookData)
    }

    override suspend fun addBookReview(accessToken: String, addBookReviewData: AddBookReviewData): Response<AddBookReviewModel> {
        return apiService.addReview(accessToken, addBookReviewData)
    }

    override suspend fun updateBookReview(accessToken: String, bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Response<UpdateBookReviewModel> {
        return apiService.updateReview(accessToken, bookCommentIdx, updateBookReviewData)
    }

    override suspend fun deleteBookReview(accessToken: String, bookCommentIdx: Int): Response<DeleteBookReviewModel> {
        return apiService.deleteReview(accessToken, bookCommentIdx)
    }

    override suspend fun bookMemo(accessToken: String, bookIsbn: String, getType: String): Response<GetBookMemoModel> {
        return apiService.bookMemo(accessToken, bookIsbn, getType)
    }

    override suspend fun addBookMemo(accessToken: String, addBookMemoData: AddBookMemoData): Response<AddBookMemoModel> {
        return apiService.addBookMemo(accessToken, addBookMemoData)
    }

    override suspend fun updateBookMemo(accessToken: String, memoIdx: Int, updateBookMemoData: UpdateBookMemoData): Response<UpdateBookMemoModel> {
        return apiService.updateMemo(accessToken, memoIdx, updateBookMemoData)
    }

    override suspend fun deleteBookMemo(accessToken: String, memoIdx: Int): Response<DeleteBookMemoModel> {
       return apiService.deleteMemo(accessToken, memoIdx)
    }

    override fun myProfile(accessToken: String): Flow<Response<MyProfileModel>> {
        return flow {
            emit(apiService.myProfile(accessToken))
        }
    }

    override fun profileActivity(accessToken: String, userIdx: Int): Flow<Response<ActivityModel>> {
        return flow {
            emit(apiService.activitiesList(accessToken, userIdx))
        }
    }

    override fun profileMemo(accessToken: String): Flow<Response<MemoModel>> {
        return flow {
            emit(apiService.memosList(accessToken))
        }
    }

    override suspend fun nicknameChange(accessToken: String, nickname: String): Response<OnlyResultModel> {
        return apiService.nicknameChange(accessToken, nickname)
    }

    override suspend fun descriptionChange(accessToken: String, description: String): Response<OnlyResultModel> {
        return apiService.descriptionChange(accessToken, description)
    }

    override fun wishBookHaveUser(accessToken: String): Flow<Response<WishBookHaveUserModel>> {
        return flow {
            emit(apiService.wishBookHaveUser(accessToken))
        }
    }

    override suspend fun createChatroom(accessToken: String, createChatroomData: CreateChatroomData): Response<ChatroomModel> {
        return apiService.createChatroom(accessToken, createChatroomData)
    }

    override suspend fun deleteChatroom(accessToken: String, chatroomIdx: Int): Response<DeleteChatroomModel> {
        return apiService.deleteChatroom(accessToken, chatroomIdx)
    }

    override fun myBookList(accessToken: String, readType: String): Flow<Response<MyBookModel>> {
        return flow {
            emit(apiService.myBookList(accessToken, readType))
        }
    }

    override fun userSetting(accessToken: String): Flow<Response<UserSettingModel>> {
        return flow {
            emit(apiService.userSetting(accessToken))
        }
    }

    override suspend fun updateUserSetting(accessToken: String, updateUserSettingData: UpdateUserSettingData): Response<OnlyResultModel> {
        return apiService.updateUserSetting(accessToken, updateUserSettingData)
    }

    override fun ticketLog(accessToken: String): Flow<Response<TicketLogModel>> {
        return flow {
            emit(apiService.ticketLog(accessToken))
        }
    }

    override suspend fun passwordChange(accessToken: String, passwordChangeData: PasswordChangeData): Response<OnlyResultModel> {
        return apiService.passwordChange(accessToken, passwordChangeData)
    }

    override suspend fun userDelete(accessToken: String): Response<OnlyResultModel> {
        return apiService.userDelete(accessToken)
    }
}