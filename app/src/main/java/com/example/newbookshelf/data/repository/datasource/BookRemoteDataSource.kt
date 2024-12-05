package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.model.find.FindPwData
import com.example.newbookshelf.data.model.home.notify.AlarmCountModel
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
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookRemoteDataSource {

    suspend fun login(loginData: LoginData): Response<LoginModel>
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
    fun searchBook(accessToken: String, bookName: String): Flow<Response<SearchBookModel>>
}