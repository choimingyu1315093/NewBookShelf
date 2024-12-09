package com.example.newbookshelf.domain.repository

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.detail.AddMyBookData
import com.example.newbookshelf.data.model.detail.AddMyBookModel
import com.example.newbookshelf.data.model.detail.DetailBookModel
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
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketModel
import com.example.newbookshelf.data.model.signup.CheckModel
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.model.signup.SignupModel
import com.example.newbookshelf.data.model.signup.SnsSignupData
import com.example.newbookshelf.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun login(loginData: LoginData): Resource<LoginModel>
    suspend fun snsLogin(snsLoginData: SnsLoginData): Resource<LoginModel>
    suspend fun findId(findIdData: FindIdData): Resource<FindModel>
    suspend fun findPw(findPwData: FindPwData): Resource<FindModel>
    suspend fun signup(signupData: SignupData): Resource<SignupModel>
    suspend fun snsSignup(snsSignupData: SnsSignupData): Resource<SignupModel>
    suspend fun idCheck(id: String): Resource<CheckModel>
    suspend fun emailCheck(emailCheckData: EmailCheckData): Resource<CheckModel>
    suspend fun nicknameCheck(nickname: String): Resource<CheckModel>
    suspend fun buyTicket(accessToken: String, ticketData: TicketData): Resource<TicketModel>
    fun alarmCount(accessToken: String): Flow<Resource<AlarmCountModel>>
    fun alarmList(accessToken: String): Flow<Resource<AlarmListModel>>
    suspend fun alarmAllDelete(accessToken: String): Resource<OnlyResultModel>
    suspend fun alarmOneDelete(accessToken: String, alarmIdx: Int): Resource<OnlyResultModel>
    suspend fun searchBook(accessToken: String, bookName: String): Resource<SearchBookModel>
    fun searchedBook(): Flow<List<SearchedBook>>
    suspend fun insert(searchedBook: SearchedBook)
    suspend fun delete(searchedBook: SearchedBook)
    suspend fun allDelete()
    suspend fun detailBook(accessToken: String, bookIsbn: String): Resource<DetailBookModel>
    suspend fun addMyBook(accessToken: String, addMyBookData: AddMyBookData): Resource<AddMyBookModel>
}