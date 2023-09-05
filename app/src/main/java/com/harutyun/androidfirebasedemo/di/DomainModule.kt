package com.harutyun.androidfirebasedemo.di

import com.harutyun.domain.repositories.UserRepository
import com.harutyun.domain.usecases.AddItemLocalUseCase
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import com.harutyun.domain.usecases.GetItemsLocalUseCase
import com.harutyun.domain.usecases.GetItemsRemoteUseCase
import com.harutyun.domain.usecases.GetUserEmailRemoteUseCase
import com.harutyun.domain.usecases.InitItemsLocalUseCase
import com.harutyun.domain.usecases.InitItemsRemoteUseCase
import com.harutyun.domain.usecases.LogOutUseCase
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

    @Provides
    fun provideInitItemsRemoteUseCase(userRepository: UserRepository): InitItemsRemoteUseCase {
        return InitItemsRemoteUseCase(userRepository)
    }

    @Provides
    fun provideInitItemsLocalUseCase(userRepository: UserRepository): InitItemsLocalUseCase {
        return InitItemsLocalUseCase(userRepository)
    }

    @Provides
    fun provideGetItemsLocalUseCase(userRepository: UserRepository): GetItemsLocalUseCase {
        return GetItemsLocalUseCase(userRepository)
    }

    @Provides
    fun provideAddItemLocalUseCase(userRepository: UserRepository): AddItemLocalUseCase {
        return AddItemLocalUseCase(userRepository)
    }

    @Provides
    fun provideLogOutUseCase(userRepository: UserRepository): LogOutUseCase {
        return LogOutUseCase(userRepository)
    }

    @Provides
    fun provideGetUserEmailRemoteUseCase(userRepository: UserRepository): GetUserEmailRemoteUseCase {
        return GetUserEmailRemoteUseCase(userRepository)
    }

}