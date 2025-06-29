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
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.presentation.di.DefaultRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class BookRemoteDataSourceImpl(@DefaultRetrofit private val apiService: ApiService): BookRemoteDataSource {

    override suspend fun login(loginData: LoginData): Response<LoginModel> {
        return apiService.login(loginData)
    }

    override suspend fun updateLocation(updateLocationData: UpdateLocationData): Response<OnlyResultModel> {
        return apiService.updateLocation(updateLocationData)
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

    override fun chatStatus(accessToken: String): Flow<Response<StatusModel>> {
        return flow {
            emit(apiService.chatStatus(accessToken))
        }
    }

    override fun alarmCount(accessToken: String): Flow<Response<AlarmCountModel>> {
        return flow {
            emit(apiService.alarmCount(accessToken))
        }
    }

    override fun alarmStatus(accessToken: String): Flow<Response<StatusModel>> {
        return flow {
            emit(apiService.alarmStatus(accessToken))
        }
    }

    override suspend fun alarmList(): Response<AlarmListModel> {
        return apiService.alarmList()
    }

    override suspend fun alarmAllDelete(): Response<OnlyResultModel> {
        return apiService.alarmAllDelete()
    }

    override suspend fun alarmOneDelete(alarmIdx: Int): Response<OnlyResultModel> {
        return apiService.alarmOneDelete(alarmIdx)
    }

    override suspend fun searchBook(bookName: String): Response<SearchBookModel> {
        return apiService.searchBook(bookName)
    }

    override suspend fun searchMoreBook(searchMoreBookData: SearchMoreBookData): Response<SearchMoreBookModel> {
        return apiService.searchMoreBook(searchMoreBookData)
    }

    override suspend fun detailBook(bookIsbn: String): Response<DetailBookModel> {
        return apiService.detailBook(bookIsbn)
    }

    override suspend fun addMyBook(addMyBookData: AddMyBookData): Response<AddMyBookModel> {
        return apiService.addMyBook(addMyBookData)
    }

    override suspend fun addBookReview(addBookReviewData: AddBookReviewData): Response<AddBookReviewModel> {
        return apiService.addReview(addBookReviewData)
    }

    override suspend fun updateBookReview(bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Response<UpdateBookReviewModel> {
        return apiService.updateReview(bookCommentIdx, updateBookReviewData)
    }

    override suspend fun deleteBookReview(bookCommentIdx: Int): Response<DeleteBookReviewModel> {
        return apiService.deleteReview(bookCommentIdx)
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

    override suspend fun myProfile(): Response<MyProfileModel> {
        return apiService.myProfile()
    }

    override fun readingStatistics(userIdx: Int): Flow<Response<ReadingStatisticsModel>> {
        return flow {
            emit(apiService.readingStatistics(userIdx))
        }
    }

    override fun profileActivity(userIdx: Int): Flow<Response<ActivityModel>> {
        return flow {
            emit(apiService.activitiesList(userIdx))
        }
    }

    override fun profileMemo(): Flow<Response<MemoModel>> {
        return flow {
            emit(apiService.memosList())
        }
    }

    override suspend fun nicknameChange(nickname: String): Response<OnlyResultModel> {
        return apiService.nicknameChange(nickname)
    }

    override suspend fun descriptionChange(description: String): Response<OnlyResultModel> {
        return apiService.descriptionChange(description)
    }

    override suspend fun topBookChange(topBookData: TopBookData): Response<OnlyResultModel> {
        return apiService.topBookChange(topBookData)
    }

    override suspend fun addPost(addPostData: AddPostData): Response<AddPostModel> {
        return apiService.addPost(addPostData)
    }


    override suspend fun postList(limit: Int, currentPage: Int): Response<PostModel> {
        return apiService.postList(limit, currentPage)
    }

    override suspend fun postDetail(postIdx: Int): Response<PostDetailModel> {
        return apiService.postDetail(postIdx)
    }

    override suspend fun postComment(postCommentData: PostCommentData): Response<PostCommentModel> {
        return apiService.postComment(postCommentData)
    }

    override suspend fun postCommentDelete(postCommentIdx: Int): Response<OnlyResultModel> {
        return apiService.postCommentDelete(postCommentIdx)
    }

    override suspend fun postDelete(postIdx: Int): Response<OnlyResultModel> {
        return apiService.postDelete(postIdx)
    }

    override suspend fun addScrap(addScrapData: AddScrapData): Response<AddScrapModel> {
        return apiService.addScrap(addScrapData)
    }

    override suspend fun addReadingClass(addReadingClassData: AddReadingClassData): Response<AddReadingClassModel> {
        return apiService.addReadingClass(addReadingClassData)
    }

    override suspend fun readingClassList(searchWord: String, filterType: String, limit: Int, currentPage: Int): Response<ReadingClassModel> {
        return apiService.readingClassList(searchWord, filterType, limit, currentPage)
    }

    override suspend fun readingClassDetail(readingClassIdx: Int): Response<ReadingClassDetailModel> {
        return apiService.readingClassDetail(readingClassIdx)
    }

    override suspend fun readingClassDelete(readingClassIdx: Int): Response<OnlyResultModel> {
        return apiService.readingClassDelete(readingClassIdx)
    }

    override suspend fun readingClassMemberList(readingClassIdx: Int, limit: Int, currentPage: Int): Response<ReadingClassMembersModel> {
        return apiService.readingClassMemberList(readingClassIdx, limit, currentPage)
    }

    override suspend fun readingClassJoin(readingClassJoinData: ReadingClassJoinData): Response<ReadingClassJoinModel> {
        return apiService.readingClassJoin(readingClassJoinData)
    }

    override suspend fun readingClassFinish(readingClassFinishData: ReadingClassFinishData): Response<OnlyResultModel> {
        return apiService.readingClassFinish(readingClassFinishData)
    }

    override fun wishBookHaveUser(): Flow<Response<WishBookHaveUserModel>> {
        return flow {
            emit(apiService.wishBookHaveUser())
        }
    }

    override suspend fun createChatroom(accessToken: String, createChatroomData: CreateChatroomData): Response<ChatroomModel> {
        return apiService.createChatroom(accessToken, createChatroomData)
    }

    override suspend fun deleteChatroom(accessToken: String, chatroomIdx: Int): Response<DeleteChatroomModel> {
        return apiService.deleteChatroom(accessToken, chatroomIdx)
    }

    override fun myBookList(readType: String): Flow<Response<MyBookModel>> {
        return flow {
            emit(apiService.myBookList(readType))
        }
    }

    override suspend fun userSetting(): Response<UserSettingModel> {
        return apiService.userSetting()
    }

    override suspend fun updateUserSetting(updateUserSettingData: UpdateUserSettingData): Response<OnlyResultModel> {
        return apiService.updateUserSetting(updateUserSettingData)
    }

    override fun ticketLog(): Flow<Response<TicketLogModel>> {
        return flow {
            emit(apiService.ticketLog())
        }
    }

    override suspend fun passwordChange(passwordChangeData: PasswordChangeData): Response<OnlyResultModel> {
        return apiService.passwordChange(passwordChangeData)
    }

    override suspend fun userDelete(): Response<OnlyResultModel> {
        return apiService.userDelete()
    }
}