package `in`.frostapps.a7minutesworkout.adapter


import `in`.frostapps.a7minutesworkout.data.HistoryEntity
import `in`.frostapps.a7minutesworkout.databinding.HistoryItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val historyList: ArrayList<HistoryEntity>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val dateTv = binding.tvDate
        val posTv = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.dateTv.text = historyList[position].date
        holder.posTv.text = (position + 1).toString()
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}