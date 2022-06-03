package com.yongjun.lottoapp.LottoRepository

import android.util.Log
import com.yongjun.lottoapp.Model.LottoModel
import com.yongjun.lottoapp.Service.RetrofitInstance
import java.text.SimpleDateFormat
import java.util.*

class LottoRepository {
    suspend fun getWinningNumber(number: Int): NewResult<LottoModel> {
        val response = RetrofitInstance.api.getRecentWinningNumber(number)
        if (response.isSuccessful) {
            response.body()?.let {
                return NewResult.Success(it)
            }

        }
        return NewResult.Error("error")
    }

    suspend fun getRecentWinningNumber(): NewResult<LottoModel> {
        var recentNumber = getNextEpisodeBasedonDate()
        val response = RetrofitInstance.api.getRecentWinningNumber(recentNumber)
        if (response.isSuccessful) {
            response.body()?.let {
                return NewResult.Success(it)
            }

        }
        return NewResult.Error("error")
    }

    fun getNextEpisodeBasedonDate(): Int {
        val startDate = "2002-12-07 23:59:59"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val cDate = Date()
        val sDate: Date = dateFormat.parse(startDate)
        val diff: Long = cDate.getTime() - sDate.getTime()
        val nextEpi = diff / (86400 * 1000 * 7) + 1
        return nextEpi.toInt()
    }
}

