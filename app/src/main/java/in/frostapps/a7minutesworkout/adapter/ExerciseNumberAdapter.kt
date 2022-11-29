package `in`.frostapps.a7minutesworkout.adapter

import `in`.frostapps.a7minutesworkout.ExerciseModel
import `in`.frostapps.a7minutesworkout.R
import `in`.frostapps.a7minutesworkout.databinding.ListItemExerciseNumberBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ExerciseNumberAdapter(private val exerciseList: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseNumberAdapter.ExerciseNumberViewHolder>() {

    class ExerciseNumberViewHolder(binding: ListItemExerciseNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvItem = binding.tvNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseNumberViewHolder {
        return ExerciseNumberViewHolder(
            ListItemExerciseNumberBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseNumberViewHolder, position: Int) {
        var model = exerciseList[position]
        holder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context, R.drawable.list_view_selected_background
                )
                holder.tvItem.setTextColor(
                    ContextCompat.getColor(
                        holder.tvItem.context, R.color.colorPrimaryDark
                    )
                )
            }
            model.getIsCompleted() -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context, R.drawable.list_view_accent_background
                )
                holder.tvItem.setTextColor(
                    ContextCompat.getColor(
                        holder.tvItem.context, R.color.white
                    )
                )
            }
            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context, R.drawable.list_view_background
                )
                holder.tvItem.setTextColor(
                    ContextCompat.getColor(
                        holder.tvItem.context, R.color.colorPrimaryDark
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}