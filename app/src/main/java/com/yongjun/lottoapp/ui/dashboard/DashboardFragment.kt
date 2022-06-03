package com.yongjun.lottoapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yongjun.lottoapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)


        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun test() {

    }

//    fun getNextEpisodeBasedonDate(): Int {
//        val startDate = "2002-12-07 23:59:59"
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//        val cDate = Date()
//        val sDate: Date = dateFormat.parse(startDate)
//        val diff: Long = cDate.getTime() - sDate.getTime()
//        val nextEpi = diff / (86400 * 1000 * 7) + 2
//        return nextEpi.toInt()
//    }
}