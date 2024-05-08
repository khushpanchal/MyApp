package com.example.myapp.common

sealed interface UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>
    data class Failure<T>(val throwable: Throwable, val data: T? = null) : UIState<T>
    data object Loading : UIState<Nothing>
    data object Empty : UIState<Nothing>
}