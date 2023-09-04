package com.harutyun.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.harutyun.domain.models.Item
import com.harutyun.domain.models.UserSignUpPayload
import kotlinx.coroutines.tasks.await

class UserFirebaseDataSource(private val firebaseAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) : UserRemoteDataSource {

    override suspend fun signUpUser(userSignUpPayload: UserSignUpPayload): AuthResult {
        return firebaseAuth
            .createUserWithEmailAndPassword(userSignUpPayload.email, userSignUpPayload.password)
            .await()
    }

    override suspend fun signInUser(userSignUpPayload: UserSignUpPayload): AuthResult {
        return firebaseAuth
            .signInWithEmailAndPassword(userSignUpPayload.email, userSignUpPayload.password)
            .await()
    }

    override suspend fun addItem(item: Item) {
        val userRef = fireStore
            .collection(USERS_COLLECTION)
            .document(firebaseAuth.uid ?: "" )

        userRef
            .update(ITEMS_FIELD, FieldValue.arrayUnion(item))
            .await()
    }


    companion object {
        private const val USERS_COLLECTION = "users"
        private const val ITEMS_FIELD = "items"

    }
}