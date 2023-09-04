package com.harutyun.domain.usecases

import com.harutyun.domain.repositories.UserRepository


class InitItemsRemoteUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(count: Int) {
        return userRepository.initItemsRemote(count)
    }
}