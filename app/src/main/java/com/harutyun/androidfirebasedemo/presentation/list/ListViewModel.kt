package com.harutyun.androidfirebasedemo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDirections
import com.google.firebase.FirebaseNetworkException
import com.harutyun.androidfirebasedemo.R
import com.harutyun.androidfirebasedemo.presentation.navigation.NavigationCommand
import com.harutyun.androidfirebasedemo.presentation.helpers.format
import com.harutyun.domain.models.Item
import com.harutyun.domain.usecases.AddItemLocalUseCase
import com.harutyun.domain.usecases.AddItemRemoteUseCase
import com.harutyun.domain.usecases.GetItemsLocalUseCase
import com.harutyun.domain.usecases.GetItemsRemoteUseCase
import com.harutyun.domain.usecases.RemoveItemLocalUseCase
import com.harutyun.domain.usecases.RemoveItemRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getItemsRemoteUseCase: GetItemsRemoteUseCase,
    private val addItemRemoteUseCase: AddItemRemoteUseCase,
    private val getItemsLocalUseCase: GetItemsLocalUseCase,
    private val addItemLocalUseCase: AddItemLocalUseCase,
    private val removeItemLocalUseCase: RemoveItemLocalUseCase,
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


    fun addItemRemote(text: String, fromLocal: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val item = Item(UUID.randomUUID().toString(), text.format())

                if (fromLocal) addItemLocalUseCase(item)
                else addItemRemoteUseCase(item)
                getItems(fromLocal = fromLocal)
            } catch (e: FirebaseNetworkException) {
                _uiState.update { it.copy(toastMessage = R.string.no_internet_message) }
            }
        }


    }

    fun removeItemRemote(position: Int, fromLocal: Boolean) {
        viewModelScope.launch {
            val itemsDiffered = viewModelScope.async(Dispatchers.IO) {
                if (fromLocal) getItemsLocalUseCase()
                else getItemsRemoteUseCase(true)
            }
            try {

                val items = itemsDiffered.await().toMutableList()

                if (fromLocal) removeItemLocalUseCase(items[position])
                else removeItemRemoteUseCase(items[position])
            } catch (e: FirebaseNetworkException) {
                _uiState.update {
                    it.copy(
                        toastMessage = R.string.no_internet_message,
                        restoredItemPosition = position
                    )
                }
            }

            getItems(fromLocal = fromLocal)

        }
    }

    fun handleBackButton(navigationBackStackEntry: NavBackStackEntry?) {
        _uiState.update { it.copy(isBackButtonVisible = navigationBackStackEntry != null) }
    }

    fun toastMessageIsShown() {
        _uiState.update { it.copy(toastMessage = 0) }
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