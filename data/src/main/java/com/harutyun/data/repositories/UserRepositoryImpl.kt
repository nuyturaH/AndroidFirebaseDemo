package com.harutyun.data.repositories

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.harutyun.data.entites.ItemEntity
import com.harutyun.data.local.UserLocalDataSource
import com.harutyun.data.mappers.ItemMapper
import com.harutyun.data.mappers.UserMapper
import com.harutyun.data.remote.UserRemoteDataSource
import com.harutyun.domain.models.Item
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.User
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.repositories.UserRepository
import java.util.UUID

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userMapper: UserMapper,
    private val itemMapper: ItemMapper
) : UserRepository {

    private var items: List<Item>? = null

    override suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): NetworkResponse<User> {
        return try {
            val firebaseUser = userRemoteDataSource.signUpUser(userSignUpPayload).user

            if (firebaseUser != null) {
                NetworkResponse.Success(userMapper.mapToDomain(firebaseUser))
            } else NetworkResponse.Failure("User is null")

        } catch (e: FirebaseAuthInvalidCredentialsException) {
            NetworkResponse.Failure(e.message.toString())
        }
    }

    override suspend fun signInUser(userSignUpPayload: UserSignUpPayload): NetworkResponse<User> {
        return try {
            val firebaseUser = userRemoteDataSource.signInUser(userSignUpPayload).user

            if (firebaseUser != null) {
                NetworkResponse.Success(userMapper.mapToDomain(firebaseUser))
            } else NetworkResponse.Failure("User is null")

        } catch (e: FirebaseAuthInvalidCredentialsException) {
            NetworkResponse.Failure(e.message.toString())
        }
    }

    override suspend fun initItemsRemote(count: Int) {
        val items = generateSequence(0) { it + 1 }
            .take(count)
            .map { ItemEntity(UUID.randomUUID().toString(), "Text ${it + 1}") }
            .toList()
        userRemoteDataSource.addItems(items)
    }

    override suspend fun getItemsFromRemote(fromCache: Boolean): List<Item> {
        if (fromCache) {
            if (items != null) {
                return items as List<Item>
            }
        }
        items = itemMapper.mapListToDomain(userRemoteDataSource.getItems(fromCache))
        return items as List<Item>
    }

    override suspend fun addItemToUserRemote(item: Item) {
        userRemoteDataSource.addItem(itemMapper.mapToData(item))
    }

    override suspend fun removeItemFromUserRemote(item: Item) {
        userRemoteDataSource.removeItem(itemMapper.mapToData(item))
    }

    override suspend fun initItemsLocal(count: Int) {
        val items = generateSequence(0) { it + 1 }
            .take(count)
            .map { ItemEntity(UUID.randomUUID().toString(), "Text ${it + 5}") }
            .toList()
        userLocalDataSource.addItems(items)
    }

    override suspend fun getItemsFromLocal(): List<Item> {

        return itemMapper.mapListToDomain(userLocalDataSource.getItems())
    }

    override suspend fun addItemLocal(item: Item) {
        userLocalDataSource.addItem(itemMapper.mapToData(item))
    }

    override suspend fun removeItemLocal(item: Item) {
        userLocalDataSource.removeItem(itemMapper.mapToData(item))
    }

    override suspend fun logOutUser() {
        userRemoteDataSource.logOutUser()
    }

    override fun getUserEmail(): String {
        return userRemoteDataSource.getUserEmail()
    }
}