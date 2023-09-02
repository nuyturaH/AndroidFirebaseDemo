package com.harutyun.androidfirebasedemo.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.models.NetworkResponse
import com.harutyun.domain.models.UserSignUpPayload
import com.harutyun.domain.usecases.SignUpByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpByEmailUseCase: SignUpByEmailUseCase
) : ViewModel() {

    fun signUpUser(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        // TODO add validations

        val userSignUpPayload = UserSignUpPayload(email, password)
        when (signUpByEmailUseCase(userSignUpPayload)) {
            is NetworkResponse.Success -> TODO("go to welcome screen")
            is NetworkResponse.Failure -> TODO("show error message")
        }
    }


}