package com.harutyun.androidfirebasedemo.presentation.list

import androidx.annotation.StringRes
import com.harutyun.domain.models.Item

data class ListUiState(
    val items: List<Item> = emptyList(),
    val isBackButtonVisible: Boolean = false,
    @StringRes val toastMessage: Int = 0,
    val restoredItemPosition: Int? = null
)