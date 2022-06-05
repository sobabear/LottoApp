package com.yongjun.lottoapp.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yongjun.lottoapp.Model.Status
import com.yongjun.lottoapp.databinding.ActivityMainBinding
import com.yongjun.lottoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
//    private var _binding: ActivityMainBinding? = null
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
            dosomething()



            mainViewModel.lottoApiResponseLiveData.observe(this) { response ->
                when (response.status) {
                    Status.LOADING -> {
                        binding.txtDate.text = "로딩 중 . . ."

                    }
                    Status.SUCCESS -> {
                        response.data?.let { lotto ->
                            binding.txtDate.text = lotto.drwNoDate
                            binding.txtNumber.text = "${lotto.drwtNo1} | ${lotto.drwtNo2} | " +
                                    "${lotto.drwtNo3} | ${lotto.drwtNo4} | ${lotto.drwtNo5} | ${lotto.drwtNo6} 보너스 : ${lotto.bnusNo}"
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

    private fun dosomething() {
//        mainViewModel.getRecentWinningNumber(binding.recentNumbers.text.toString().toInt())
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
}