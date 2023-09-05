package com.harutyun.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harutyun.data.entites.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class RoomItemsDatabase : RoomDatabase() {

    abstract val roomItemsDao: RoomItemsDao
}
