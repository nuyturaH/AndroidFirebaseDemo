package com.harutyun.androidfirebasedemo.presentation.list

import com.harutyun.domain.models.Item

data class ListUiState(
    val items: List<Item> = emptyList(),
    val isBackButtonVisible: Boolean = false
)