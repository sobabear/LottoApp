package com.yongjun.lottoapp.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yongjun.lottoapp.LottoRepository.LottoRepository
import com.yongjun.lottoapp.Model.LottoModel
import com.yongjun.lottoapp.R

class TotalNumberAdapter(private val context: Context): RecyclerView.Adapter<TotalNumberAdapter.ViewHolder>() {


    var datas = listOf<LottoModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TotalNumberAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.total_list,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return datas.count()
    }

    override fun onBindViewHolder(holder: TotalNumberAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtNumber: TextView = itemView.findViewById(R.id.tv_number)
        private val txtRound: TextView = itemView.findViewById(R.id.tv_round)

        fun bind(lotto: LottoModel) {
            txtNumber.text = "${lotto.drwtNo1} | ${lotto.drwtNo2} | " +
                    "${lotto.drwtNo3} | ${lotto.drwtNo4} | ${lotto.drwtNo5} | ${lotto.drwtNo6} 보너스 : ${lotto.bnusNo}"
            txtRound.text = "${lotto.drwNo} 회 (${lotto.drwNoDate})"

        }
    }
}