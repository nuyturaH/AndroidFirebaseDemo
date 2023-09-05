package com.harutyun.domain.usecases

import com.harutyun.domain.repositories.UserRepository


class InitItemsLocalUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(count: Int) {
        return userRepository.initItemsLocal(count)
    }
}