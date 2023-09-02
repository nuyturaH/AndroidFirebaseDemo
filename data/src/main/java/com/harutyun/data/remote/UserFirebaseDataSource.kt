package com.harutyun.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.harutyun.domain.models.UserSignUpPayload
import kotlinx.coroutines.tasks.await

class UserFirebaseDataSource(private val firebaseAuth: FirebaseAuth) : UserRemoteDataSource {

    override suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): AuthResult {
        return firebaseAuth
            .createUserWithEmailAndPassword(userSignUpPayload.email, userSignUpPayload.password)
            .await()
    }
}