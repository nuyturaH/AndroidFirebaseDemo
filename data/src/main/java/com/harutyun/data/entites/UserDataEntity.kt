package com.harutyun.data.entites

data class UserDataEntity(val items: List<ItemEntity>) {
    constructor() : this(emptyList())

}