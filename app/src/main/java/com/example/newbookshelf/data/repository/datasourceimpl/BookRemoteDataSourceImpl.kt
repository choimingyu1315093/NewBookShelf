package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.model.find.FindPwData
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
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import retrofit2.Response

class BookRemoteDataSourceImpl(private val apiService: ApiService): BookRemoteDataSource {

    override suspend fun login(loginData: LoginData): Response<LoginModel> {
        return apiService.login(loginData)
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
}