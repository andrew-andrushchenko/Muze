package com.andrii_a.muze.core

sealed interface BackendResult<out T> {
    object Empty : BackendResult<Nothing>
    object Loading : BackendResult<Nothing>
    data class Success<out T>(val value: T) : BackendResult<T>
    data class Error(val code: Int? = null, val reason: String? = null) : BackendResult<Nothing>

}