package com.harutyun.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemEntity(@PrimaryKey val id: String, val text: String) {
    constructor() : this("", "")
}