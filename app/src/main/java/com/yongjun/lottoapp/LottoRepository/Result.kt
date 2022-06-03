package com.yongjun.lottoapp.LottoRepository

sealed class NewResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NewResult<T>()
    data class Error(val error: String) : NewResult<Nothing>()
}