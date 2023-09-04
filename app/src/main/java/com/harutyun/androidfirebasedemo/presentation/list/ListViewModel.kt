package com.harutyun.androidfirebasedemo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.models.Item
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import com.harutyun.domain.usecases.GetItemsRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getItemsRemoteUseCase: GetItemsRemoteUseCase,
    private val addItemRemoteUseCase: AddItemRemoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getItems(true)
        }
    }

    private suspend fun getItems(fromCache: Boolean) {
        val itemsDiffered = viewModelScope.async(Dispatchers.IO) {
            getItemsRemoteUseCase(fromCache)
        }
        _uiState.update { it.copy(items = itemsDiffered.await()) }
    }


    fun addItemRemote(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemRemoteUseCase(item)

            getItems(false)
        }
    }
}