package com.testdai.model

sealed class State <T> (open val data: T?) {
    data class Loading <T> (override val data: T? = null): State<T>(data)
    data class Success <T> (override val data: T): State<T>(data)
    data class Error <T> (override val data: T? = null, val message: String? = null): State<T>(data)

}