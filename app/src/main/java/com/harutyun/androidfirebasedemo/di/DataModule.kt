package com.harutyun.androidfirebasedemo.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.harutyun.data.local.UserLocalDataSource
import com.harutyun.data.local.room.RoomItemLocalDataSource
import com.harutyun.data.local.room.RoomItemsDao
import com.harutyun.data.local.room.RoomItemsDatabase
import com.harutyun.data.mappers.ItemMapper
import com.harutyun.data.mappers.UserMapper
import com.harutyun.data.remote.UserFirebaseDataSource
import com.harutyun.data.remote.UserRemoteDataSource
import com.harutyun.data.repositories.UserRepositoryImpl
import com.harutyun.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: UserLocalDataSource,
        userMapper: UserMapper,
        itemMapper: ItemMapper
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, localDataSource, userMapper, itemMapper)
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
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Singleton
    @Provides
    fun provideItemMapper(): ItemMapper {
        return ItemMapper()
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


    @Singleton
    @Provides
    fun provideRoomItemDataBase(@ApplicationContext context: Context): RoomItemsDatabase {
        return Room.databaseBuilder(
            context,
            RoomItemsDatabase::class.java,
            "RoomItemDatabase.db"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideRoomItemDao(dataBase: RoomItemsDatabase): RoomItemsDao {
        return dataBase.roomItemsDao
    }

    @Singleton
    @Provides
    fun provideUserRoomDataSource(dao: RoomItemsDao): UserLocalDataSource {
        return RoomItemLocalDataSource(dao)
    }
}