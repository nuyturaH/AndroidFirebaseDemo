package com.harutyun.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harutyun.data.entites.ItemEntity

@Dao
interface RoomItemsDao {

    @Query("SELECT * FROM item_table")
    suspend fun getItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)
}
