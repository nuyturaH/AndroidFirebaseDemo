package com.harutyun.androidfirebasedemo.presentation.signin

import androidx.annotation.StringRes

data class SignInUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    @StringRes val errorMessage: Int = 0
)