package com.harutyun.androidfirebasedemo.presentation.signup

import androidx.lifecycle.ViewModel
import com.harutyun.domain.usecases.SignUpByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpByEmailUseCase: SignUpByEmailUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}