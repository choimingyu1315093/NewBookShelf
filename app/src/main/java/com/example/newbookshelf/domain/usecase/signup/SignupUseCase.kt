package com.example.newbookshelf.domain.usecase.signup

import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.model.signup.SignupModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class SignupUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(signupData: SignupData): Resource<SignupModel> {
        return bookRepository.signup(signupData)
    }
}