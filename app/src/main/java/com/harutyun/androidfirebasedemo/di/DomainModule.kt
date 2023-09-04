package com.harutyun.androidfirebasedemo.di

import com.harutyun.domain.repositories.UserRepository
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import com.harutyun.domain.usecases.GetItemsRemoteUseCase
import com.harutyun.domain.usecases.RemoveItemRemoteUseCase
import com.harutyun.domain.usecases.SignInByEmailUseCase
import com.harutyun.domain.usecases.SignUpByEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideSignUpByEmailUseCase(userRepository: UserRepository): SignUpByEmailUseCase {
        return SignUpByEmailUseCase(userRepository)
    }

    @Provides
    fun provideSignInByEmailUseCase(userRepository: UserRepository): SignInByEmailUseCase {
        return SignInByEmailUseCase(userRepository)
    }

    @Provides
    fun provideAddItemRemoteUseCase(userRepository: UserRepository): AddItemRemoteUseCase {
        return AddItemRemoteUseCase(userRepository)
    }

    @Provides
    fun provideRemoveItemRemoteUseCase(userRepository: UserRepository): RemoveItemRemoteUseCase {
        return RemoveItemRemoteUseCase(userRepository)
    }

    @Provides
    fun provideGetItemsRemoteUseCase(userRepository: UserRepository): GetItemsRemoteUseCase {
        return GetItemsRemoteUseCase(userRepository)
    }

}