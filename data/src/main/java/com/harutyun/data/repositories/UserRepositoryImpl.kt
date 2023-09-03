package com.harutyun.data.repositories

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.harutyun.data.mappers.UserMapper
import com.harutyun.data.remote.UserRemoteDataSource
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.User
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userMapper: UserMapper
) : UserRepository {

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
}