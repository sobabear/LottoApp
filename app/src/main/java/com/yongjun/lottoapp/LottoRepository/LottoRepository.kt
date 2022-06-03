package com.yongjun.lottoapp.LottoRepository

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.yongjun.lottoapp.Model.LottoModel
import com.yongjun.lottoapp.Service.RetrofitInstance
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class LottoRepository {
    companion object {
        var lottoList: MutableList<LottoModel>
        var recentLottoOrder = getNextEpisodeBasedonDate()
        private lateinit var database: DatabaseReference

        init {
            database = FirebaseDatabase.getInstance().reference
            lottoList = mutableListOf()

            database.child("lottoLists").get().addOnSuccessListener {
//                lottoList = it.getValue() as MutableList<LottoModel>
                for (i in it.children) {
                    var lotto = i.getValue(LottoModel::class.java)
                    Log.d("😭2", "lotto ${lotto}")
                    lotto?.let {
                        lottoList.add(it)
                    }
                }
            }.addOnCompleteListener {
                GlobalScope.launch {
                    lottoList += getAllNumber().await()
                    Log.d("😭2", "Success ${lottoList.count()}")
                    Log.d("😭2", "Success ${lottoList}")
                }
            }
        }

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

        suspend fun getAllNumber(): Deferred<MutableList<LottoModel>> = coroutineScope {
            var recentNumber = getNextEpisodeBasedonDate()
            var lottoLists: MutableList<LottoModel> = mutableListOf()
            Log.d("😭 getAllnumber","fun")
            async {
                for(i: Int in 1001 .. recentNumber) {
                    Log.d("😭 getAllnumber"," for ${i}")
                    var response = RetrofitInstance.api.getRecentWinningNumber(i)
                    response.body()?.let {
                        lottoLists.add(it)
                    }
                }
                return@async lottoLists
            }
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
}

