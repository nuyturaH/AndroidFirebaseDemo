package com.harutyun.data.remote

import com.google.firebase.auth.AuthResult
import com.harutyun.data.entites.ItemEntity
import com.harutyun.domain.models.UserSignUpPayload

interface UserRemoteDataSource {

    suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): AuthResult

    suspend fun signInUser(userSignUpPayload: UserSignUpPayload): AuthResult

    suspend fun initItems(count: Int)

    suspend fun getItems(fromCache: Boolean): List<ItemEntity>

    suspend fun addItem(item: ItemEntity)

    suspend fun removeItem(item: ItemEntity)

    suspend fun logOutUser()

    fun getUserEmail(): String

}