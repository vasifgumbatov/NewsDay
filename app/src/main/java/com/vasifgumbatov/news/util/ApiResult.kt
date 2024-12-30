package com.vasifgumbatov.news.util

import com.vasifgumbatov.news.data.remote.response.ErrorModel

sealed class ApiState<out T> {
    data class Success<T>(val data: T?): ApiState<T>()
    data class Error(val error: ErrorModel?): ApiState<Nothing>()
}

