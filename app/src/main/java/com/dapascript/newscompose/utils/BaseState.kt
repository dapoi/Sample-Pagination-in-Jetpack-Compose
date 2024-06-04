package com.dapascript.newscompose.utils

sealed class BaseState<out T : Any?> {
    data object Loading : BaseState<Nothing>()
    data class Success<out T : Any>(val data: T) : BaseState<T>()
    data class Error(val error: Throwable) : BaseState<Nothing>()
}