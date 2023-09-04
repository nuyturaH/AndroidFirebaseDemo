package com.harutyun.androidfirebasedemo.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.harutyun.androidfirebasedemo.presentation.NavigationCommand
import com.harutyun.androidfirebasedemo.presentation.helpers.isLongEnough
import com.harutyun.androidfirebasedemo.presentation.helpers.isValidEmail
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.usecases.GetItemsRemoteUseCase
import com.harutyun.domain.usecases.InitItemsRemoteUseCase
import com.harutyun.domain.usecases.SignUpByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpByEmailUseCase: SignUpByEmailUseCase,
    private val initItemsRemoteUseCase: InitItemsRemoteUseCase,
    private val getItemsRemoteUseCase: GetItemsRemoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableStateFlow<NavigationCommand>(NavigationCommand.None)
    val navigation = _navigation.asStateFlow()


    fun signUpUser(email: String, password: String) {
        if (isCredentialsValid(email, password)) {
            _uiState.update { it.copy(isLoading = true) }

            viewModelScope.launch(Dispatchers.IO) {

                val userSignUpPayload = UserSignUpPayload(email, password)
                when (val signUp = signUpByEmailUseCase(userSignUpPayload)) {
                    is NetworkResponse.Success -> {
                        initItemsRemoteUseCase(5)
                        getItemsRemoteUseCase(false)
                        goToWelcomeFragment()
                    }
                    is NetworkResponse.Failure -> _uiState.update { it.copy(passwordErrorMessage = signUp.errorMessage) }
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

    private fun goToWelcomeFragment() {
        navigate(SignUpFragmentDirections.actionSignUpFragmentToWelcomeFragment())
    }

    fun goToSignInFragment() {
        navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
    }

    fun navigationClear() {
        _navigation.update { NavigationCommand.None }
    }

}