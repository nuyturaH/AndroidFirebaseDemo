package com.harutyun.data.mappers

import com.harutyun.data.entites.ItemEntity
import com.harutyun.domain.models.Item

class ItemMapper {

    fun mapToDomain(input: ItemEntity): Item {
        return Item(
            id = input.id,
            text = input.text
        )
    }

    fun mapListToDomain(input: List<ItemEntity>): List<Item> {
        return input.map { mapToDomain(it) }
    }

    fun mapToData(input: Item): ItemEntity {
        return ItemEntity(
            id = input.id,
            text = input.text
        )
    }

}