package com.harutyun.domain.usecases

import com.harutyun.domain.models.Item
import com.harutyun.domain.repositories.UserRepository

class RemoveItemLocalUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(item: Item) {
        return userRepository.removeItemLocal(item)
    }
}