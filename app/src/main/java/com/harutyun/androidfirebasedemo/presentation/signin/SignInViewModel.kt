package com.harutyun.androidfirebasedemo.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import com.harutyun.androidfirebasedemo.presentation.helpers.isLongEnough
import com.harutyun.androidfirebasedemo.presentation.helpers.isValidEmail
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.usecases.SignInByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInByEmailUseCase: SignInByEmailUseCase
) : ViewModel() {
    private val _navigation = MutableStateFlow<NavigationCommand>(NavigationCommand.None)
    val navigation = _navigation.asStateFlow()

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()


    fun signInUser(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }
        if (isCredentialsValid(email, password)) {

            viewModelScope.launch(Dispatchers.IO) {

                val userSignUpPayload = UserSignUpPayload(email, password)
                when (val signIn = signInByEmailUseCase(userSignUpPayload)) {
                    is NetworkResponse.Success -> {/* go to list screen */
                    }

                    is NetworkResponse.Failure -> _uiState.update { it.copy(emailErrorMessage = signIn.errorMessage) }
                }

                _uiState.update { it.copy(isLoading = false) }
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


    private fun navigate(navDirections: NavDirections) {
        _navigation.update { NavigationCommand.ToDirection(navDirections) }
    }

    fun goToSignUpFragment() {
        navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
    }

    fun navigationClear() {
        _navigation.update { NavigationCommand.None }
    }
}