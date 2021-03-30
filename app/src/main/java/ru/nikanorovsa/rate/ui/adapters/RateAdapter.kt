package ru.nikanorovsa.rate.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nikanorovsa.rate.R
import ru.nikanorovsa.rate.data.room.RateModel
import ru.nikanorovsa.rate.databinding.LayoutViewHolderBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class RateAdapter(private var multiplier: Double = 0.0) : ListAdapter<RateModel, RateAdapter.RateViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false), multiplier)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate: RateModel = getItem(position)
        holder.bind(rate)
    }

    fun changeMultiplier(multi: Double) {
        multiplier = multi
        notifyDataSetChanged()
    }

    class RateViewHolder(private val binding: LayoutViewHolderBinding, private val multiplier: Double) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: RateModel) {
            val change = rate.previous.toDouble() - rate.value.toDouble()
            binding.apply {
                viewHolderLayoutNameOfValuta.text = rate.name
                if (change > 0.0) viewHolderLayoutCourse.setTextColor(Color.GREEN)
                if (change < 0.0) viewHolderLayoutCourse.setTextColor(Color.RED)
                if (change == 0.0) viewHolderLayoutCourse.setTextColor(Color.BLUE)
                viewHolderLayoutCourse.text = rate.value
                val dec = DecimalFormat("#.##")
                dec.roundingMode = RoundingMode.CEILING
                val resultOfCount = (multiplier / rate.value.toDouble()).toBigDecimal()
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

