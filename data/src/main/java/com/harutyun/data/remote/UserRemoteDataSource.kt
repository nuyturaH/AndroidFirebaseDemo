package com.harutyun.data.remote

import com.google.firebase.auth.AuthResult
import com.harutyun.domain.models.UserSignUpPayload

interface UserRemoteDataSource {

    suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): AuthResult

}