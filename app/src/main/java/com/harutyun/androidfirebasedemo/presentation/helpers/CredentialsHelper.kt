package com.harutyun.androidfirebasedemo.presentation.helpers

import android.util.Patterns

class CredentialsHelper

fun String.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isLongEnough() = length >= 6


