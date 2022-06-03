package com.yongjun.lottoapp.Model

import android.provider.VoicemailContract

data class LottoApiResponse(
    val status: Status,
    val data: LottoModel?,
    val error: String?
) {
    companion object {
        fun loading(): LottoApiResponse = LottoApiResponse(Status.LOADING, null, null)

        fun success(lotto: LottoModel): LottoApiResponse =
            LottoApiResponse(Status.SUCCESS, lotto, null)

        fun error(error: String): LottoApiResponse =
            LottoApiResponse(Status.ERROR, null, error)
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}