package com.harutyun.androidfirebasedemo.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import com.harutyun.androidfirebasedemo.presentation.signin.SignInFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {
    private val _navigation = MutableStateFlow<NavigationCommand>(NavigationCommand.None)
    val navigation = _navigation.asStateFlow()

    private fun navigate(navDirections: NavDirections) {
        _navigation.update { NavigationCommand.ToDirection(navDirections) }
    }

    fun goToListFragment() {
        navigate(SignInFragmentDirections.actionSignInFragmentToListFragment())
    }

    fun navigationClear() {
        _navigation.update { NavigationCommand.None }
    }
}