package ru.nikanorovsa.rate

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.for_view_holder.view.*
import ru.nikanorovsa.rate.model.RateModel
import java.math.RoundingMode
import java.text.DecimalFormat

// Стандартный класс для создания RecyclerView, единственная его особенность в том, что он дополнительно
//принимает var edit: Double  для обработки данных из editTextNumber макета основной активности и вывода
// их в RecyclerView
class Recycler(
    val rateList: MutableList<RateModel>,
    val context: Context,
    var edit: Double
) :
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
            ), edit

        )
    }

    class ViewHolder(view: View, edit: Double) : RecyclerView.ViewHolder(view) {


        val valuteName = view.name_of_valute
        val course = view.course
        val youGet = view.youGet
        var cash = edit
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate: RateModel = rateList[position]
        val collorDouble =
            rateList[position].previous.toDouble() - rateList[position].value.toDouble()
        Log.d("eee","$collorDouble")
        if (collorDouble == 0.0) {

            holder.course.setBackgroundColor(Color.YELLOW)

        }
        if (collorDouble > 0.0) {

            holder.course.setBackgroundColor(Color.GREEN)

        }
        if (collorDouble < 0.0) {

            holder.course.setBackgroundColor(Color.RED)

        }


        holder.valuteName.text = rate.name
        holder.course.text = rate.value
        val dec = DecimalFormat("#.####")
        dec.roundingMode = RoundingMode.CEILING
        val a = (holder.cash / rate.value.toDouble()).toBigDecimal()
        holder.youGet.text = dec.format(a).toString()
    }


}

