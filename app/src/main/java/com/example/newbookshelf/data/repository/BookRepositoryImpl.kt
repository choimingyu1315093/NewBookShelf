package com.example.newbookshelf.data.repository

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
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
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
import com.example.newbookshelf.data.repository.datasource.BookLocalDataSource
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class BookRepositoryImpl(private val bookRemoteDataSource: BookRemoteDataSource, private val bookLocalDataSource: BookLocalDataSource): BookRepository {

    override suspend fun login(loginData: LoginData): Resource<LoginModel> {
        return responseToResource(bookRemoteDataSource.login(loginData))
    }

    override suspend fun updateLocation(updateLocationData: UpdateLocationData): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.updateLocation(updateLocationData))
    }

    override suspend fun snsLogin(snsLoginData: SnsLoginData): Resource<LoginModel> {
        return responseToResource(bookRemoteDataSource.snsLogin(snsLoginData))
    }

    override suspend fun findId(findIdData: FindIdData): Resource<FindModel> {
        return responseToResource(bookRemoteDataSource.findId(findIdData))
    }

    override suspend fun findPw(findPwData: FindPwData): Resource<FindModel> {
        return responseToResource(bookRemoteDataSource.findPw(findPwData))
    }

    override suspend fun signup(signupData: SignupData): Resource<SignupModel> {
        return responseToResource(bookRemoteDataSource.signup(signupData))
    }

    override suspend fun snsSignup(snsSignupData: SnsSignupData): Resource<SignupModel> {
        return responseToResource(bookRemoteDataSource.snsSignup(snsSignupData))
    }

    override suspend fun idCheck(id: String): Resource<CheckModel> {
        return responseToResource(bookRemoteDataSource.idCheck(id))
    }

    override suspend fun emailCheck(emailCheckData: EmailCheckData): Resource<CheckModel> {
        return responseToResource(bookRemoteDataSource.emailCheck(emailCheckData))
    }

    override suspend fun nicknameCheck(nickname: String): Resource<CheckModel> {
        return responseToResource(bookRemoteDataSource.nicknameCheck(nickname))
    }

    override suspend fun buyTicket(accessToken: String, ticketData: TicketData): Resource<TicketModel> {
        return responseToResource(bookRemoteDataSource.buyTicket(accessToken, ticketData))
    }

    override fun chatStatus(accessToken: String): Flow<Resource<StatusModel>> {
        return bookRemoteDataSource.chatStatus(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun alarmCount(accessToken: String): Flow<Resource<AlarmCountModel>> {
        return bookRemoteDataSource.alarmCount(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun alarmStatus(accessToken: String): Flow<Resource<StatusModel>> {
        return bookRemoteDataSource.alarmStatus(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun alarmList(): Resource<AlarmListModel> {
        return responseToResource(bookRemoteDataSource.alarmList())
    }

    override suspend fun alarmAllDelete(): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.alarmAllDelete())
    }

    override suspend fun alarmOneDelete(alarmIdx: Int): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.alarmOneDelete(alarmIdx))
    }

    override suspend fun searchBook(bookName: String): Resource<SearchBookModel> {
        return responseToResource(bookRemoteDataSource.searchBook(bookName))
    }

    override suspend fun searchMoreBook(searchMoreBookData: SearchMoreBookData): Resource<SearchMoreBookModel> {
        return responseToResource(bookRemoteDataSource.searchMoreBook(searchMoreBookData))
    }

    override fun searchedBook(): Flow<List<SearchedBook>> {
        return bookLocalDataSource.getSearchedBook()
    }

    override suspend fun insert(searchedBook: SearchedBook) {
        bookLocalDataSource.insert(searchedBook)
    }

    override suspend fun delete(searchedBook: SearchedBook) {
        bookLocalDataSource.delete(searchedBook)
    }

    override suspend fun allDelete() {
        bookLocalDataSource.allDelete()
    }

    override suspend fun detailBook(bookIsbn: String): Resource<DetailBookModel> {
        return responseToResource(bookRemoteDataSource.detailBook(bookIsbn))
    }

    override suspend fun addMyBook(addMyBookData: AddMyBookData): Resource<AddMyBookModel> {
        return responseToResource(bookRemoteDataSource.addMyBook(addMyBookData))
    }

    override suspend fun addBookReview(addBookReviewData: AddBookReviewData): Resource<AddBookReviewModel> {
        return responseToResource(bookRemoteDataSource.addBookReview(addBookReviewData))
    }

    override suspend fun updateBookReview(bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Resource<UpdateBookReviewModel> {
        return responseToResource(bookRemoteDataSource.updateBookReview(bookCommentIdx, updateBookReviewData))
    }

    override suspend fun deleteBookReview(bookCommentIdx: Int): Resource<DeleteBookReviewModel> {
        return responseToResource(bookRemoteDataSource.deleteBookReview(bookCommentIdx))
    }

    override suspend fun bookMemo(accessToken: String, bookIsbn: String, getType: String): Resource<GetBookMemoModel> {
        return responseToResource(bookRemoteDataSource.bookMemo(accessToken, bookIsbn, getType))
    }

    override suspend fun addBookMemo(accessToken: String, addBookMemoData: AddBookMemoData): Resource<AddBookMemoModel> {
        return responseToResource(bookRemoteDataSource.addBookMemo(accessToken, addBookMemoData))
    }

    override suspend fun updateBookMemo(accessToken: String, bookMemoIdx: Int, updateBookMemoData: UpdateBookMemoData): Resource<UpdateBookMemoModel> {
        return responseToResource(bookRemoteDataSource.updateBookMemo(accessToken, bookMemoIdx, updateBookMemoData))
    }

    override suspend fun deleteBookMemo(accessToken: String, bookMemoIdx: Int): Resource<DeleteBookMemoModel> {
        return responseToResource(bookRemoteDataSource.deleteBookMemo(accessToken, bookMemoIdx))
    }

    override suspend fun myProfile(): Resource<MyProfileModel> {
        return responseToResource(bookRemoteDataSource.myProfile())
    }

    override fun readingStatistics(userIdx: Int): Flow<Resource<ReadingStatisticsModel>> {
        return bookRemoteDataSource.readingStatistics(userIdx).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun profileActivity(userIdx: Int): Flow<Resource<ActivityModel>> {
        return bookRemoteDataSource.profileActivity(userIdx).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun profileMemo(): Flow<Resource<MemoModel>> {
        return bookRemoteDataSource.profileMemo().map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun nicknameChange(nickname: String): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.nicknameChange(nickname))
    }

    override suspend fun descriptionChange(description: String): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.descriptionChange(description))
    }

    override suspend fun topBookChange(topBookData: TopBookData): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.topBookChange(topBookData))
    }

    override suspend fun addPost(addPostData: AddPostData): Resource<AddPostModel> {
        return responseToResource(bookRemoteDataSource.addPost(addPostData))
    }

    override suspend fun postList(limit: Int, currentPage: Int): Resource<PostModel> {
        return responseToResource(bookRemoteDataSource.postList(limit, currentPage))
    }

    override suspend fun postDetail(postIdx: Int): Resource<PostDetailModel> {
        return responseToResource(bookRemoteDataSource.postDetail(postIdx))
    }

    override suspend fun postComment(postCommentData: PostCommentData): Resource<PostCommentModel> {
        return responseToResource(bookRemoteDataSource.postComment(postCommentData))
    }

    override suspend fun postCommentDelete(postCommentIdx: Int): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.postCommentDelete(postCommentIdx))
    }

    override suspend fun postDelete(postIdx: Int): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.postDelete(postIdx))
    }

    override suspend fun addScrap(addScrapData: AddScrapData): Resource<AddScrapModel> {
        return responseToResource(bookRemoteDataSource.addScrap(addScrapData))
    }

    override suspend fun addReadingClass(addReadingClassData: AddReadingClassData): Resource<AddReadingClassModel> {
        return responseToResource(bookRemoteDataSource.addReadingClass(addReadingClassData))
    }

    override suspend fun readingClassList(searchWord: String, filterType: String, limit: Int, currentPage: Int): Resource<ReadingClassModel> {
        return responseToResource(bookRemoteDataSource.readingClassList(searchWord, filterType, limit, currentPage))
    }

    override suspend fun readingClassDetail(readingClassIdx: Int): Resource<ReadingClassDetailModel> {
        return responseToResource(bookRemoteDataSource.readingClassDetail(readingClassIdx))
    }

    override suspend fun readingClassDelete(readingClassIdx: Int): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.readingClassDelete(readingClassIdx))
    }

    override suspend fun readingClassMemberList(readingClassIdx: Int, limit: Int, currentPage: Int): Resource<ReadingClassMembersModel> {
        return responseToResource(bookRemoteDataSource.readingClassMemberList(readingClassIdx, limit, currentPage))
    }

    override suspend fun readingClassJoin(readingClassJoinData: ReadingClassJoinData): Resource<ReadingClassJoinModel> {
        return responseToResource(bookRemoteDataSource.readingClassJoin(readingClassJoinData))
    }

    override suspend fun readingClassFinish(readingClassFinishData: ReadingClassFinishData): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.readingClassFinish(readingClassFinishData))
    }

    override fun wishBookHaveUser(): Flow<Resource<WishBookHaveUserModel>> {
        return bookRemoteDataSource.wishBookHaveUser().map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun createChatroom(accessToken: String, createChatroomData: CreateChatroomData): Resource<ChatroomModel> {
        return responseToResource(bookRemoteDataSource.createChatroom(accessToken, createChatroomData))
    }

    override suspend fun deleteChatroom(accessToken: String, chatroomIdx: Int): Resource<DeleteChatroomModel> {
        return responseToResource(bookRemoteDataSource.deleteChatroom(accessToken, chatroomIdx))
    }

    override fun myBookList(readType: String): Flow<Resource<MyBookModel>> {
        return bookRemoteDataSource.myBookList(readType).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun userSetting(): Resource<UserSettingModel> {
        return responseToResource(bookRemoteDataSource.userSetting())
    }


    override suspend fun updateUserSetting(updateUserSettingData: UpdateUserSettingData): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.updateUserSetting(updateUserSettingData))
    }

    override fun ticketLog(): Flow<Resource<TicketLogModel>> {
        return bookRemoteDataSource.ticketLog().map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun passwordChange(passwordChangeData: PasswordChangeData): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.passwordChange(passwordChangeData))
    }

    override suspend fun userDelete(): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.userDelete())
    }

//    private fun responseToResource(response: Response<LoginModel>): Resource<LoginModel>{
//        if(response.isSuccessful){
//            response.body()?.let { result ->
//                return Resource.Success(result)
//            }
//        }
//        return Resource.Error(response.message())
//    }

    private fun <T> responseToResource(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                Resource.Success(result)
            } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error(response.message())
        }
    }

}