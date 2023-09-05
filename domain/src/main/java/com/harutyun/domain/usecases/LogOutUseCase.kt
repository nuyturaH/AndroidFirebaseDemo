package com.harutyun.domain.usecases

import com.harutyun.domain.repositories.UserRepository

class LogOutUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke() {
        return userRepository.logOutUser()
    }
}