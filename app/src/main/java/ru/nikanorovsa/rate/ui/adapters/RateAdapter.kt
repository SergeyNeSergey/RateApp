package ru.nikanorovsa.rate.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nikanorovsa.rate.data.room.RateModel
import ru.nikanorovsa.rate.databinding.LayoutViewHolderBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class RateAdapter(private var multiplier: Double = 0.0) : ListAdapter<RateModel, RateAdapter.RateViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate: RateModel = getItem(position)
        holder.bind(rate, multiplier)
    }

    fun changeMultiplier(multi: Double) {
        multiplier = multi
        notifyDataSetChanged()
    }

    class RateViewHolder(private val binding: LayoutViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: RateModel, multiplier: Double) {
            val change = rate.previous - rate.value
            binding.apply {
                val dec = DecimalFormat("#.##")
                dec.roundingMode = RoundingMode.CEILING
                viewHolderLayoutNameOfValuta.text = rate.name
                if (change > 0.0) viewHolderLayoutCourse.setTextColor(Color.GREEN)
                if (change < 0.0) viewHolderLayoutCourse.setTextColor(Color.RED)
                if (change == 0.0) viewHolderLayoutCourse.setTextColor(Color.BLUE)
                viewHolderLayoutCourse.text = dec.format(rate.value.toBigDecimal()).toString()
                val resultOfCount = (multiplier / rate.value).toBigDecimal()
                viewHolderLayoutResult.text = dec.format(resultOfCount).toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RateModel>() {
        override fun areItemsTheSame(oldItem: RateModel, newItem: RateModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RateModel, newItem: RateModel) = oldItem == newItem
    }
}

