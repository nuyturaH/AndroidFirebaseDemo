package com.harutyun.domain.usecases

import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.User
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.repositories.UserRepository

class SignUpByEmailUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(userSignUpPayload: UserSignUpPayload): NetworkResponse<User> {
        return userRepository.signUpUser(userSignUpPayload)
    }
}