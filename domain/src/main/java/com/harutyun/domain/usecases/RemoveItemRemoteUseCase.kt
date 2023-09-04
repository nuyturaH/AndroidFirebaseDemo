package com.harutyun.domain.usecases

import com.harutyun.domain.models.Item
import com.harutyun.domain.repositories.UserRepository

class RemoveItemRemoteUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(item: Item) {
        return userRepository.removeItemFromUserRemote(item)
    }
}