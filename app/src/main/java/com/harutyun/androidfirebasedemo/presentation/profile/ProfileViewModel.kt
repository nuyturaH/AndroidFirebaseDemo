package com.harutyun.androidfirebasedemo.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import com.harutyun.domain.usecases.GetUserEmailRemoteUseCase
import com.harutyun.domain.usecases.LogOutUseCase
import com.harutyun.domain.usecases.RemoveAllItemsLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserEmailRemoteUseCase: GetUserEmailRemoteUseCase,
    private val removeAllItemsLocalUseCase: RemoveAllItemsLocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableStateFlow<NavigationCommand>(NavigationCommand.None)
    val navigation = _navigation.asStateFlow()

    init {
        _uiState.update { it.copy(email = getUserEmailRemoteUseCase()) }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            removeAllItemsLocalUseCase()
            logOutUseCase()
            goToSignInFragment()
        }
    }

    private fun navigate(navDirections: NavDirections) {
        _navigation.update { NavigationCommand.ToDirection(navDirections) }
    }

    private fun goToSignInFragment() {
        navigate(ProfileFragmentDirections.actionProfileFragmentToSignInFragment())
    }


    fun navigationClear() {
        _navigation.update { NavigationCommand.None }
    }


    fun navigateBack() {
        _navigation.update { NavigationCommand.Back }
    }
}