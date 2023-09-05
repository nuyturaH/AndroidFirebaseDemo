package com.harutyun.domain.repositories

import com.harutyun.domain.models.Item
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.User
import com.harutyun.domain.models.UserSignUpPayload

interface UserRepository {

    suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): NetworkResponse<User>

    suspend fun signInUser(userSignUpPayload: UserSignUpPayload): NetworkResponse<User>

    suspend fun initItemsRemote(count: Int)

    suspend fun getItemsFromRemote(fromCache: Boolean): List<Item>

    suspend fun addItemToUserRemote(item: Item)

    suspend fun removeItemFromUserRemote(item: Item)

    suspend fun initItemsLocal(count: Int)

    suspend fun getItemsFromLocal(): List<Item>

    suspend fun addItemLocal(item: Item)

    suspend fun removeItemLocal(item: Item)

    fun removeItemsLocal()

    suspend fun logOutUser()

    fun getUserEmail(): String

}