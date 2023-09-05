package com.harutyun.domain.models

sealed class NetworkResponse<out R> {
    class Success<out T>(val data: T) : NetworkResponse<T>()
    class Failure<T>(val errorMessage: String) : NetworkResponse<T>()
    class NoInternet<T> : NetworkResponse<T>()
}
