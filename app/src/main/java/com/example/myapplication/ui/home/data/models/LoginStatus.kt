package com.example.myapplication.ui.home.data.models

sealed class LoginState<out T> {
    data class Success<T>(val data: T) : LoginState<T>()
    data class Error(val error: String) : LoginState<Nothing>()
    object Loading : LoginState<Nothing>()
    object Init : LoginState<Nothing>()
}