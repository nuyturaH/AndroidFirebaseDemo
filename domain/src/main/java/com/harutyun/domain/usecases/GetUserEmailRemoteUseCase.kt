package com.harutyun.domain.usecases

import com.harutyun.domain.repositories.UserRepository


class GetUserEmailRemoteUseCase(private val userRepository: UserRepository) {

    operator fun invoke(): String {
        return userRepository.getUserEmail()
    }
}