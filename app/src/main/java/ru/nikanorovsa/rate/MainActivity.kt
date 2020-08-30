package ru.nikanorovsa.rate

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.nikanorovsa.rate.model.RateModel
import ru.nikanorovsa.rate.retrofit.RetrofitFactory.rateController
import ru.nikanorovsa.rate.room.AppDatabase
import ru.nikanorovsa.rate.room.RateDao
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    var listRate: MutableList<RateModel> = ArrayList()
    private var dataBase: AppDatabase? = null
    private var rateDao: RateDao? = null
    var databaseSize: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBase = AppDatabase.getAppDataBase(context = this)
        rateDao = dataBase?.rateDao()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = Recycler(listRate, this)
        val observable = rateDao!!.getAll()
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RateModel> {
                override fun onError(e: Throwable?) {
                    val text = e.toString()
                    val duration = Toast.LENGTH_LONG

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                }


                override fun onCompleted() {
                    recycler.adapter!!.notifyDataSetChanged()


                }

                override fun onNext(t: RateModel?) {
                    if (t != null) {
                        listRate.add(t)
                    }

                }

            })
        if (listRate.size == 0) {
            initRateList()
        }
    }

    private fun initRateList() {
        progressBar.visibility = View.VISIBLE
        val observable = rateController!!.getRateAsync()
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RateModel>> {
                override fun onError(e: Throwable?) {
                    val text = e.toString()
                    val duration = Toast.LENGTH_LONG

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()

                }


                override fun onCompleted() {


                }


                override fun onNext(t: List<RateModel>?) {
                    if (t != null) {
                        rateDao?.insertAll(t)
                    }

                }

            })

    }

}


