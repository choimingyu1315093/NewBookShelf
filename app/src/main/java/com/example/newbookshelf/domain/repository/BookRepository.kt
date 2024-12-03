package com.example.newbookshelf.domain.repository

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
import com.example.newbookshelf.data.util.Resource

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
}