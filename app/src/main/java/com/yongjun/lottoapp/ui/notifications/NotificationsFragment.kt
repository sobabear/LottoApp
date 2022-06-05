package com.yongjun.lottoapp.ui.notifications

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.yongjun.lottoapp.LottoRepository.LottoRepository
import com.yongjun.lottoapp.R
import com.yongjun.lottoapp.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: TotalNumberAdapter
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = root.findViewById(R.id.total_recyler_view)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        adapter = container?.let { TotalNumberAdapter(it.context) }!!
        while (!isOnline(container!!.context)) {

            Toast.makeText(container!!.context, "인터넷 상태를 확인해주세요. 그리고 앱을 다시 실행 시켜주세요", Toast.LENGTH_LONG).show()
            Thread.sleep(2500)
        }
        Toast.makeText(container!!.context, " 로딩중입니다. 잠시만 기다려주세요", Toast.LENGTH_SHORT).show()

        init()

        adapter.notifyDataSetChanged()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {


        recyclerView.adapter = adapter

        adapter.datas = LottoRepository.lottoList.reversed().toList()
        Log.d("⚽️adapter dats11", "${adapter.datas}")
        GlobalScope.launch {
            while (LottoRepository.lottoList.count() != LottoRepository.recentLottoOrder) {
                delay(1000)
                Log.d("⚽️adapter dats22", "${LottoRepository.lottoList.count()}")
                launch(Dispatchers.Main) {
                    adapter.datas = LottoRepository.lottoList.reversed().toList()
                    adapter.notifyDataSetChanged()
                }
            }
        }
        Log.d("⚽️adapter dats33", "${adapter.datas}")
        adapter.notifyDataSetChanged()
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