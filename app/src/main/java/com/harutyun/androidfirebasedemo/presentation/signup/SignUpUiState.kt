package com.harutyun.androidfirebasedemo.presentation.signup

import androidx.annotation.StringRes

data class SignUpUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    @StringRes val errorMessage: Int = 0,
    val signUpButtonClicked: Boolean = false,
    val signInButtonClicked: Boolean = false
)