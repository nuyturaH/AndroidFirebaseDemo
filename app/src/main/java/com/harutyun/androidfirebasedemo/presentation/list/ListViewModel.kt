package com.harutyun.androidfirebasedemo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import com.harutyun.domain.models.Item
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import com.harutyun.domain.usecases.GetItemsRemoteUseCase
import com.harutyun.domain.usecases.RemoveItemRemoteUseCase
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
    private val addItemRemoteUseCase: AddItemRemoteUseCase,
    private val removeItemRemoteUseCase: RemoveItemRemoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableStateFlow<NavigationCommand>(NavigationCommand.None)
    val navigation = _navigation.asStateFlow()

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

    fun removeItemRemote(position: Int) {
        viewModelScope.launch {
            val itemsDiffered = viewModelScope.async(Dispatchers.IO) {
                getItemsRemoteUseCase(true)
            }

            val items = itemsDiffered.await().toMutableList()
            removeItemRemoteUseCase(items[position])

            getItems(false)
        }
    }

    private fun navigate(navDirections: NavDirections) {
        _navigation.update { NavigationCommand.ToDirection(navDirections) }
    }

    fun goToProfileFragment() {
        navigate(ListFragmentDirections.actionListFragmentToProfileFragment())
    }

    fun navigateBack() {
        _navigation.update { NavigationCommand.Back }
    }

    fun navigationClear() {
        _navigation.update { NavigationCommand.None }
    }
}