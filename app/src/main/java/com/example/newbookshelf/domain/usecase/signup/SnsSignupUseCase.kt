package com.example.newbookshelf.domain.usecase.signup

import com.example.newbookshelf.data.model.signup.SignupModel
import com.example.newbookshelf.data.model.signup.SnsSignupData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class SnsSignupUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(snsSignupData: SnsSignupData): Resource<SignupModel> {
        return bookRepository.snsSignup(snsSignupData)
    }
}