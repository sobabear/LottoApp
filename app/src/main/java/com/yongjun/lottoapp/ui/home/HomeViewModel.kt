package com.yongjun.lottoapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongjun.lottoapp.LottoRepository.LottoRepository
import com.yongjun.lottoapp.LottoRepository.NewResult
import com.yongjun.lottoapp.Model.LottoApiResponse
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _lottoApiResponseLiveData = MutableLiveData<LottoApiResponse>()
    val lottoApiResponseLiveData: MutableLiveData<LottoApiResponse> get() = _lottoApiResponseLiveData

    private val _recentLottoApiResponseLiveData = MutableLiveData<LottoApiResponse>()
    val recentLottoApiResponseLiveData: MutableLiveData<LottoApiResponse> get() = _recentLottoApiResponseLiveData

    fun getWinningNumber(number: Int) = viewModelScope.launch {
        _lottoApiResponseLiveData.value = LottoApiResponse.loading()

        when (val result = LottoRepository.getWinningNumber(number)) {
            is NewResult.Success -> {
                _lottoApiResponseLiveData.value = LottoApiResponse.success(result.data)
            }
            is NewResult.Error -> {
                _lottoApiResponseLiveData.value = LottoApiResponse.error(result.error)
            }
        }
    }

    fun getRecentWinningNumber() = viewModelScope.launch {
        _recentLottoApiResponseLiveData.value = LottoApiResponse.loading()
        Log.d("❤️ log", LottoRepository.getRecentWinningNumber().toString())
        when (val result = LottoRepository.getRecentWinningNumber()) {
            is NewResult.Success -> {
                _recentLottoApiResponseLiveData.value = LottoApiResponse.success(result.data)
            }

            is NewResult.Error -> {
                _recentLottoApiResponseLiveData.value = LottoApiResponse.error(result.error)
            }
        }

    }

}