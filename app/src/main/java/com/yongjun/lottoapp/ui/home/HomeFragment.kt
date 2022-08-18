package com.yongjun.lottoapp.ui.home

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yongjun.lottoapp.Model.LottoModel
import com.yongjun.lottoapp.Model.Status
import com.yongjun.lottoapp.R
import com.yongjun.lottoapp.databinding.ActivityMainBinding
import com.yongjun.lottoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (isOnline(container!!.context)) {
            binding.btnSearch.setOnClickListener {
                mainViewModel.getWinningNumber(binding.edtRound.text.toString().toInt())
            }

            mainViewModel.getRecentWinningNumber()

            mainViewModel.lottoApiResponseLiveData.observe(this) { response ->
                when (response.status) {
                    Status.LOADING -> {
                        binding.txtDate.text = "로딩 중 . . ."

                    }
                    Status.SUCCESS -> {
                        response.data?.let { lotto ->
                            binding.txtDate.text = lotto.drwNoDate
                            configure(container!!.context, lotto, binding)
                        }
                    }
                    Status.ERROR -> {
                        binding.txtDate.text = "에러가 났어요"

                    }
                }
            }

            mainViewModel.recentLottoApiResponseLiveData.observe(this) { response ->
                when  (response.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        response.data?.let { lotto ->
                            binding.recentNumbers.text = "${lotto.drwtNo1} | ${lotto.drwtNo2} | " +
                                    "${lotto.drwtNo3} | ${lotto.drwtNo4} | ${lotto.drwtNo5} | ${lotto.drwtNo6} 보너스 : ${lotto.bnusNo}"
                            binding.recentNumberTitle.text = "${lotto.drwNo}회(최신)"

                        }
                    }
                    Status.ERROR -> {
                        binding.recentNumbers.text = "에러가 났어요"
                    }
                }
            }
        } else {

            binding.btnSearch.setOnClickListener {
                Toast.makeText(container!!.context, "에러가 났어요, 인터넷 연결상태를 확인해주세요", 2000).show()
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun configure(context: Context, lotto: LottoModel, binding: FragmentHomeBinding) {

        binding.number1.text = "${lotto.drwtNo1}"
        configureColor(lotto.drwtNo1, binding.number1, context)

        binding.number2.text = "${lotto.drwtNo2}"
        configureColor(lotto.drwtNo2, binding.number2, context)

        binding.number3.text = "${lotto.drwtNo3}"
        configureColor(lotto.drwtNo3, binding.number3, context)

        binding.number4.text = "${lotto.drwtNo4}"
        configureColor(lotto.drwtNo4, binding.number4, context)

        binding.number5.text = "${lotto.drwtNo5}"
        configureColor(lotto.drwtNo5, binding.number5, context)

        binding.number6.text = "${lotto.drwtNo6}"
        configureColor(lotto.drwtNo6, binding.number6, context)

        binding.bonusNumber.text = "${lotto.bnusNo}"
        configureColor(lotto.bnusNo, binding.bonusNumber, context)
    }

    fun configureColor(_number: Int?, textView: TextView, context: Context) {
        var number: Int = _number ?: 50
        if (number < 10 && number >= 1) {
            textView.background = ContextCompat.getDrawable(context, R.drawable.radius_tens)
        } else if (number < 20 && number >= 10) {
            textView.background = ContextCompat.getDrawable(context, R.drawable.radius_twenties)
        } else if (number < 30 && number >= 20) {
            textView.background = ContextCompat.getDrawable(context, R.drawable.radius_twenties)
        } else if (number < 40 && number >= 30) {
            textView.background = ContextCompat.getDrawable(context, R.drawable.radius_thirties)
        } else {
            textView.background = ContextCompat.getDrawable(context, R.drawable.radius_forties)
        }
    }
}