package com.harutyun.domain.usecases

import com.harutyun.domain.models.Item
import com.harutyun.domain.repositories.UserRepository


class GetItemsRemoteUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(fromCache: Boolean): List<Item> {
        return userRepository.getItemsFromRemote(fromCache)
    }
}