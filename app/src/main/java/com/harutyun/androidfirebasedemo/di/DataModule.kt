package com.harutyun.androidfirebasedemo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.harutyun.data.mappers.UserMapper
import com.harutyun.data.remote.UserFirebaseDataSource
import com.harutyun.data.remote.UserRemoteDataSource
import com.harutyun.data.repositories.UserRepositoryImpl
import com.harutyun.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        userMapper: UserMapper
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, userMapper)
    }

    @Singleton
    @Provides
    fun provideUserFirebaseDataSource(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): UserRemoteDataSource {
        return UserFirebaseDataSource(auth, fireStore)
    }

    @Singleton
    @Provides
    fun providePizzaMapper(): UserMapper {
        return UserMapper()
    }

    @Singleton
    @Provides
    fun provideFireAuthentication(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }


}