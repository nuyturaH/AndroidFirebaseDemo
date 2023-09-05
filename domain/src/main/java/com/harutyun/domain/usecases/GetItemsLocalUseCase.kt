package com.harutyun.domain.usecases

import com.harutyun.domain.models.Item
import com.harutyun.domain.repositories.UserRepository


class GetItemsLocalUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): List<Item> {
        return userRepository.getItemsFromLocal()
    }
}