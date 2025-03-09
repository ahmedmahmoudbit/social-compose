package com.example.utils

//sealed class ResourceState<T> {
//    data class Success<T>(val data: T): ResourceState<T>()
//    data class Error<T>(val error: String): ResourceState<T>()
//    class Loading<T>: ResourceState<T>()
//    class Init<T>: ResourceState<T>()
//}

sealed class ResourceState<out T> {
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error(val error: String) : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
    object Init : ResourceState<Nothing>()
}

