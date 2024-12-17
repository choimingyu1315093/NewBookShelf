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
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketModel
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

    override suspend fun updateLocation(accessToken: String, updateLocationData: UpdateLocationData): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.updateLocation(accessToken, updateLocationData))
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

    override fun alarmCount(accessToken: String): Flow<Resource<AlarmCountModel>> {
        return bookRemoteDataSource.alarmCount(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun alarmList(accessToken: String): Flow<Resource<AlarmListModel>> {
        return bookRemoteDataSource.alarmList(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun alarmAllDelete(accessToken: String): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.alarmAllDelete(accessToken))
    }

    override suspend fun alarmOneDelete(accessToken: String, alarmIdx: Int): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.alarmOneDelete(accessToken, alarmIdx))
    }

    override suspend fun searchBook(accessToken: String, bookName: String): Resource<SearchBookModel> {
        return responseToResource(bookRemoteDataSource.searchBook(accessToken, bookName))
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

    override suspend fun detailBook(accessToken: String, bookIsbn: String): Resource<DetailBookModel> {
        return responseToResource(bookRemoteDataSource.detailBook(accessToken, bookIsbn))
    }

    override suspend fun addMyBook(accessToken: String, addMyBookData: AddMyBookData): Resource<AddMyBookModel> {
        return responseToResource(bookRemoteDataSource.addMyBook(accessToken, addMyBookData))
    }

    override suspend fun addBookReview(accessToken: String, addBookReviewData: AddBookReviewData): Resource<AddBookReviewModel> {
        return responseToResource(bookRemoteDataSource.addBookReview(accessToken, addBookReviewData))
    }

    override suspend fun updateBookReview(accessToken: String, bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Resource<UpdateBookReviewModel> {
        return responseToResource(bookRemoteDataSource.updateBookReview(accessToken, bookCommentIdx, updateBookReviewData))
    }

    override suspend fun deleteBookReview(accessToken: String, bookCommentIdx: Int): Resource<DeleteBookReviewModel> {
        return responseToResource(bookRemoteDataSource.deleteBookReview(accessToken, bookCommentIdx))
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

    override fun myProfile(accessToken: String): Flow<Resource<MyProfileModel>> {
        return bookRemoteDataSource.myProfile(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun profileActivity(accessToken: String, userIdx: Int): Flow<Resource<ActivityModel>> {
        return bookRemoteDataSource.profileActivity(accessToken, userIdx).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun profileMemo(accessToken: String): Flow<Resource<MemoModel>> {
        return bookRemoteDataSource.profileMemo(accessToken).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override suspend fun nicknameChange(accessToken: String, nickname: String): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.nicknameChange(accessToken, nickname))
    }

    override suspend fun descriptionChange(accessToken: String, description: String): Resource<OnlyResultModel> {
        return responseToResource(bookRemoteDataSource.descriptionChange(accessToken, description))
    }

    override fun wishBookHaveUser(accessToken: String): Flow<Resource<WishBookHaveUserModel>> {
        return bookRemoteDataSource.wishBookHaveUser(accessToken).map { response ->
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

    override fun myBookList(accessToken: String, readType: String): Flow<Resource<MyBookModel>> {
        return bookRemoteDataSource.myBookList(accessToken, readType).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
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