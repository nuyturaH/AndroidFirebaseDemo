package com.harutyun.androidfirebasedemo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDirections
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import com.harutyun.domain.models.Item
import com.harutyun.domain.usecases.AddItemLocalUseCase
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import com.harutyun.domain.usecases.GetItemsLocalUseCase
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
    private val getItemsLocalUseCase: GetItemsLocalUseCase,
    private val addItemLocalUseCase: AddItemLocalUseCase,
    private val removeItemRemoteUseCase: RemoveItemRemoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableStateFlow<NavigationCommand>(NavigationCommand.None)
    val navigation = _navigation.asStateFlow()

    init {
        viewModelScope.launch {
            getItems(fromCache = true, fromLocal = false)
        }
    }

    fun getItems(fromCache: Boolean = false, fromLocal: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val itemsDiffered = viewModelScope.async {
                if (fromLocal) {
                    getItemsLocalUseCase()
                } else {
                    getItemsRemoteUseCase(fromCache)
                }
            }
            _uiState.update { it.copy(items = itemsDiffered.await()) }
        }
    }


    fun addItemRemote(item: Item, fromLocal: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (fromLocal) addItemLocalUseCase(item)
            else addItemRemoteUseCase(item)
            getItems(fromLocal = fromLocal)
        }
    }

    fun removeItemRemote(position: Int, fromLocal: Boolean) {
        viewModelScope.launch {
            val itemsDiffered = viewModelScope.async(Dispatchers.IO) {
                getItemsRemoteUseCase(true)
            }

            val items = itemsDiffered.await().toMutableList()
            removeItemRemoteUseCase(items[position])

            getItems(fromLocal = fromLocal)
        }
    }

    fun handleBackButton(navigationBackStackEntry: NavBackStackEntry?) {
        _uiState.update { it.copy(isBackButtonVisible = navigationBackStackEntry != null) }
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