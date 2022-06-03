package com.yongjun.lottoapp.Service
import com.yongjun.lottoapp.Model.LottoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoApi {

    @GET("/common.do")
    suspend fun getRecentWinningNumber(
        @Query("drwNo") drwNum: Int,
        @Query("method") method: String = "getLottoNumber"
    ): Response<LottoModel>

}