package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.domain.repository.AladinBookRepository
import com.example.newbookshelf.domain.repository.BookRepository
import com.example.newbookshelf.domain.usecase.chat.CreateChatroomUseCase
import com.example.newbookshelf.domain.usecase.chat.DeleteChatroomUseCase
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase
import com.example.newbookshelf.domain.usecase.detail.AddBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.AddBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.BookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookReviewUseCase
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmCountUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmListUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmOneDeleteUseCase
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
import com.example.newbookshelf.domain.usecase.login.UpdateLocationUseCase
import com.example.newbookshelf.domain.usecase.profile.DescriptionChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.MyProfileUseCase
import com.example.newbookshelf.domain.usecase.profile.NicknameChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileActivityUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileMemoUseCase
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
    fun provideUpdateLocationUseCase(bookRepository: BookRepository): UpdateLocationUseCase {
        return UpdateLocationUseCase(bookRepository)
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
    fun provideAlarmListUseCase(bookRepository: BookRepository): AlarmListUseCase {
        return AlarmListUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideAlarmAllDeleteUseCase(bookRepository: BookRepository): AlarmAllDeleteUseCase {
        return AlarmAllDeleteUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideAlarmOneDeleteUseCase(bookRepository: BookRepository): AlarmOneDeleteUseCase {
        return AlarmOneDeleteUseCase(bookRepository)
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

    @Singleton
    @Provides
    fun provideDetailBookUseCase(bookRepository: BookRepository): DetailBookUseCase {
        return DetailBookUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideAddMyBookUseCase(bookRepository: BookRepository): AddMyBookUseCase {
        return AddMyBookUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideAddBookReviewUseCase(bookRepository: BookRepository): AddBookReviewUseCase {
        return AddBookReviewUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateBookReviewUseCase(bookRepository: BookRepository): UpdateBookReviewUseCase {
        return UpdateBookReviewUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteBookReviewUseCase(bookRepository: BookRepository): DeleteBookReviewUseCase {
        return DeleteBookReviewUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideBookMemoUseCase(bookRepository: BookRepository): BookMemoUseCase {
        return BookMemoUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideAddBookMemoUseCase(bookRepository: BookRepository): AddBookMemoUseCase {
        return AddBookMemoUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateBookMemoUseCase(bookRepository: BookRepository): UpdateBookMemoUseCase {
        return UpdateBookMemoUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteBookMemoUseCase(bookRepository: BookRepository): DeleteBookMemoUseCase {
        return DeleteBookMemoUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideMyProfileUseCase(bookRepository: BookRepository): MyProfileUseCase {
        return MyProfileUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideProfileActivityUseCase(bookRepository: BookRepository): ProfileActivityUseCase {
        return ProfileActivityUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideProfileMemoUseCase(bookRepository: BookRepository): ProfileMemoUseCase {
        return ProfileMemoUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideNicknameChangeUseCase(bookRepository: BookRepository): NicknameChangeUseCase {
        return NicknameChangeUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideDescriptionChangeUseCase(bookRepository: BookRepository): DescriptionChangeUseCase {
        return DescriptionChangeUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideWishBookHaveUserUseCase(bookRepository: BookRepository): WishBookHaveUserUseCase {
        return WishBookHaveUserUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideCreateChatroomUseCase(bookRepository: BookRepository): CreateChatroomUseCase {
        return CreateChatroomUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteChatroomUseCase(bookRepository: BookRepository): DeleteChatroomUseCase {
        return DeleteChatroomUseCase(bookRepository)
    }
}