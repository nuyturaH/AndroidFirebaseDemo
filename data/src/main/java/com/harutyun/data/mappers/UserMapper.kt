package com.harutyun.data.mappers

import com.google.firebase.auth.FirebaseUser
import com.harutyun.domain.models.User

class UserMapper {

    fun mapToDomain(input: FirebaseUser): User {
        return User(
            email = input.email ?: "",
            password = "",
            items = listOf()
        )
    }

}