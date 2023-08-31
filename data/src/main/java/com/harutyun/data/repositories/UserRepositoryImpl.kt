package com.harutyun.data.repositories

import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.repositories.UserRepository

class UserRepositoryImpl: UserRepository {
    override suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): Boolean {
        TODO("Not yet implemented")
    }
}