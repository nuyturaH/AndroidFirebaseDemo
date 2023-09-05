package com.harutyun.data.local.room

import com.harutyun.data.entites.ItemEntity
import com.harutyun.data.local.UserLocalDataSource

class RoomItemLocalDataSource(private val roomItemDao: RoomItemsDao) : UserLocalDataSource {

    override suspend fun getItems(): List<ItemEntity> {
        return roomItemDao.getItems()
    }

    override suspend fun addItem(item: ItemEntity) {
        roomItemDao.insertItem(item)
    }

    override suspend fun removeItem(item: ItemEntity) {
        roomItemDao.deleteItem(item)
    }

}
