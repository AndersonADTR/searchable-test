package com.test.anderson.searchable.base

import retrofit2.HttpException

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(exception: Exception): Resource<T> {
        return when (exception) {
            is HttpException -> Resource.error(getErrorMessage(exception.code()), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}