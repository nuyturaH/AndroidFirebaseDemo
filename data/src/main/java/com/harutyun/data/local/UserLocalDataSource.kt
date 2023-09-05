package com.harutyun.data.local

import com.harutyun.data.entites.ItemEntity

interface UserLocalDataSource {

    suspend fun getItems(): List<ItemEntity>

    suspend fun addItem(item: ItemEntity)

    suspend fun addItems(items: List<ItemEntity>)

    suspend fun removeItem(item: ItemEntity)

    fun removeItems()
}