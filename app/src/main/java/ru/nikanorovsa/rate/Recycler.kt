package ru.nikanorovsa.rate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.editTextNumber
import kotlinx.android.synthetic.main.for_view_holder.view.*
import ru.nikanorovsa.rate.model.RateModel


class Recycler(val rateList: MutableList<RateModel>, val context: Context) :
    RecyclerView.Adapter<Recycler.ViewHolder>() {

    override fun getItemCount(): Int {
        return rateList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.for_view_holder,
                parent,
                false
            )
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val valuteName = view.name_of_valute
        val course = view.course
        val youGet = view.youGet
        var cash = view.editTextNumber.text.toString().toDouble()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate: RateModel = rateList[position]

        holder.valuteName.text = rate.name
        holder.course.text = rate.value
        holder.youGet.text = (rate.value.toDouble() * holder.cash).toString()
    }
}

