package com.harutyun.domain.usecases

import com.harutyun.domain.repositories.UserRepository

class RemoveAllItemsLocalUseCase(private val userRepository: UserRepository) {

    operator fun invoke() {
        return userRepository.removeItemsLocal()
    }
}