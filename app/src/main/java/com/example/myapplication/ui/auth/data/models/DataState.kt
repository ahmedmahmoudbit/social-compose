package com.example.myapplication.ui.auth.data.models

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data object Init : DataState<Nothing>()
}

// Keep LoginState for backward compatibility  
typealias LoginState<T> = DataState<T>