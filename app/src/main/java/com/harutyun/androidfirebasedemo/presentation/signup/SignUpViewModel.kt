package com.harutyun.androidfirebasedemo.presentation.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.usecases.SignUpByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpByEmailUseCase: SignUpByEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun signUpUser(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }
        if (isCredentialsValid(email, password)) {

            viewModelScope.launch(Dispatchers.IO) {

                val userSignUpPayload = UserSignUpPayload(email, password)
                when (val signUp = signUpByEmailUseCase(userSignUpPayload)) {
                    is NetworkResponse.Success -> TODO("go to welcome screen")
                    is NetworkResponse.Failure -> {
                        _uiState.update { it.copy(emailErrorMessage = signUp.errorMessage) }
                    }
                }

                _uiState.update { it.copy(isLoading = true) }

            }
        }
    }

    private fun isCredentialsValid(email: String, password: String): Boolean {
        _uiState.update { it.copy(emailErrorMessage = "", passwordErrorMessage = "") }


        if (email.isEmpty()) {
            _uiState.update { it.copy(emailErrorMessage = "Email is empty") }
        } else if (!email.isValidEmail()) {
            _uiState.update { it.copy(emailErrorMessage = "Email format is wrong") }
        }

        if (password.isEmpty()) {
            _uiState.update { it.copy(passwordErrorMessage = "Password is empty") }
        } else if (!password.isLongEnough()) {
            _uiState.update { it.copy(passwordErrorMessage = "Password should have more than 6 symbols") }
        }

        _uiState.update { it.copy(isLoading = false) }

        return uiState.value.emailErrorMessage.isEmpty() && uiState.value.passwordErrorMessage.isEmpty()
    }

    private fun String.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun String.isLongEnough() = length >= 6

}