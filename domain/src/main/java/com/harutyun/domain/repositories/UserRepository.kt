package com.harutyun.domain.repositories

import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.User
import com.harutyun.domain.models.UserSignUpPayload

interface UserRepository {

    suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): NetworkResponse<User>

}