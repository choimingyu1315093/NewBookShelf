package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.domain.repository.AladinBookRepository
import com.example.newbookshelf.domain.repository.BookRepository
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmCountUseCase
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import com.example.newbookshelf.domain.usecase.login.IdLoginUseCase
import com.example.newbookshelf.domain.usecase.login.SnsLoginUseCase
import com.example.newbookshelf.domain.usecase.setting.BuyTicketUseCase
import com.example.newbookshelf.domain.usecase.signup.EmailCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.IdCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.NicknameCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.SignupUseCase
import com.example.newbookshelf.domain.usecase.signup.SnsSignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideIdLoginUseCase(bookRepository: BookRepository): IdLoginUseCase {
        return IdLoginUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSnsLoginUseCase(bookRepository: BookRepository): SnsLoginUseCase {
        return SnsLoginUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideFindIdUseCase(bookRepository: BookRepository): FindIdUseCase {
        return FindIdUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideFindPwUseCase(bookRepository: BookRepository): FindPwUseCase {
        return FindPwUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSignupUseCase(bookRepository: BookRepository): SignupUseCase {
        return SignupUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSnsSignupUseCase(bookRepository: BookRepository): SnsSignupUseCase {
        return SnsSignupUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideIdCheckUseCase(bookRepository: BookRepository): IdCheckUseCase {
        return IdCheckUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideEmailCheckUseCase(bookRepository: BookRepository): EmailCheckUseCase {
        return EmailCheckUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideNicknameCheckUseCase(bookRepository: BookRepository): NicknameCheckUseCase {
        return NicknameCheckUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideBuyTicketUseCase(bookRepository: BookRepository): BuyTicketUseCase {
        return BuyTicketUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideWeekBestsellerUseCase(aladinBookRepository: AladinBookRepository): WeekBestsellerUseCase {
        return WeekBestsellerUseCase(aladinBookRepository)
    }

    @Singleton
    @Provides
    fun provideNewBestsellerUseCase(aladinBookRepository: AladinBookRepository): NewBestsellerUseCase {
        return NewBestsellerUseCase(aladinBookRepository)
    }

    @Singleton
    @Provides
    fun provideAttentionBestsellerUseCase(aladinBookRepository: AladinBookRepository): AttentionBestsellerUseCase {
        return AttentionBestsellerUseCase(aladinBookRepository)
    }

    @Singleton
    @Provides
    fun provideAlarmCountUseCase(bookRepository: BookRepository): AlarmCountUseCase {
        return AlarmCountUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSearchBookUseCase(bookRepository: BookRepository): SearchBookUseCase {
        return SearchBookUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedBookUseCase(bookRepository: BookRepository): SearchedBookUseCase {
        return SearchedBookUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedBookInsertUseCase(bookRepository: BookRepository): SearchedBookInsertUseCase {
        return SearchedBookInsertUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedBookDeleteUseCase(bookRepository: BookRepository): SearchedBookDeleteUseCase {
        return SearchedBookDeleteUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedBookAllDeleteUseCase(bookRepository: BookRepository): SearchedBookAllDeleteUseCase {
        return SearchedBookAllDeleteUseCase(bookRepository)
    }
}