package com.example.myapplication.ui.auth.data.models

sealed class AuthState<out T> {
    data class Success<T>(val data: T) : AuthState<T>()
    data class Error(val error: String) : AuthState<Nothing>()
    data object Loading : AuthState<Nothing>()
    data object Init : AuthState<Nothing>()
}

// Keep LoginState for backward compatibility  
typealias LoginState<T> = AuthState<T>