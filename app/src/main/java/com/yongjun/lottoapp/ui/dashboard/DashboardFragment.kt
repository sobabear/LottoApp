package com.yongjun.lottoapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yongjun.lottoapp.LottoRepository.LottoRepository
import com.yongjun.lottoapp.R
import com.yongjun.lottoapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var button: Button
    private lateinit var edtTextView: TextView
    private lateinit var recomendationTextView: TextView
    private var lottoMap = mutableMapOf(1 to 0)
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        for(i in 1..45) {
            lottoMap[i] = 0
        }
        val dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        button = dashboardView.findViewById(R.id.dashboardSearchbutton)
        recomendationTextView = dashboardView.findViewById(R.id.recomendationTextView)
        edtTextView = dashboardView.findViewById(R.id.editTextNumber)
        if (container != null) {
            setupButton(container.context)
        }

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)


        val root: View = binding.root

        return dashboardView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButton(context: Context) {
        var tempLottoMap = lottoMap
        button.setOnClickListener {
            if (LottoRepository.lottoList.count() < LottoRepository.recentLottoOrder) {
                Log.d("😀", "It is small")
                android.widget.Toast.makeText(context, "waitwait", 2)
            }
            var a = edtTextView.text.toString().toInt()
            var gap = LottoRepository.recentLottoOrder - a
            for (i in gap..LottoRepository.recentLottoOrder - 1) {
                Log.d("😀", " loop ${i} ${LottoRepository.lottoList[i]}")
                var lotto = LottoRepository.lottoList[i]

                tempLottoMap[lotto.drwtNo1!!] = (tempLottoMap[lotto.drwtNo1!!] ?: 0) + 1
                tempLottoMap[lotto.drwtNo2!!] = (tempLottoMap[lotto.drwtNo2!!] ?: 0) + 1
                tempLottoMap[lotto.drwtNo3!!] = (tempLottoMap[lotto.drwtNo3!!] ?: 0) + 1
                tempLottoMap[lotto.drwtNo4!!] = (tempLottoMap[lotto.drwtNo4!!] ?: 0) + 1
                tempLottoMap[lotto.drwtNo5!!] = (tempLottoMap[lotto.drwtNo5!!] ?: 0) + 1
                tempLottoMap[lotto.drwtNo6!!] = (tempLottoMap[lotto.drwtNo6!!] ?: 0) + 1
                tempLottoMap[lotto.bnusNo!!] = (tempLottoMap[lotto.bnusNo!!] ?: 0) + 1
            }
            Log.d("😀", " clicked ${tempLottoMap}")
            Log.d("😀", " ${edtTextView.text}")

        }

    }
}