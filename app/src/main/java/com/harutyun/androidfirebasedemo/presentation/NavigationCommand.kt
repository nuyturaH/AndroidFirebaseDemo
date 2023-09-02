package com.harutyun.androidfirebasedemo.presentation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data object Back : NavigationCommand()
    data object None : NavigationCommand()
}