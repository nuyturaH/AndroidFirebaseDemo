package com.harutyun.androidfirebasedemo.presentation.signup

import androidx.annotation.StringRes

data class SignUpUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    @StringRes val emailErrorMessageId: Int = 0,
    @StringRes val passwordErrorMessageId: Int = 0,
    @StringRes val errorMessageId: Int = 0,
    val errorMessage: String = ""
)