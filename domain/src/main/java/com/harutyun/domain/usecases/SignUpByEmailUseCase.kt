package com.harutyun.domain.usecases

import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.repositories.UserRepository

class SignUpByEmailUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(userSignUpPayload: UserSignUpPayload): Boolean {
        return userRepository.signUpUser(userSignUpPayload)
    }
}