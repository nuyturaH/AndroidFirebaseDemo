package com.harutyun.domain.models

data class User(val email: String, val password: String, val items: List<Item>)