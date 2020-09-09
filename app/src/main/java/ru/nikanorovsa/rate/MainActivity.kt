package ru.nikanorovsa.rate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.activity_main.*
import ru.nikanorovsa.rate.model.Json4KotlinBase
import ru.nikanorovsa.rate.model.RateModel
import ru.nikanorovsa.rate.retrofit.RetrofitFactory.rateController
import ru.nikanorovsa.rate.room.AppDatabase
import ru.nikanorovsa.rate.room.RateDao
import java.util.concurrent.TimeUnit

//Основной класс приложения
class MainActivity : AppCompatActivity() {
    //Лист поставляемый в  Recycler. Обновляется в методе initRateList и сохраняется в баззе данных
    var listRate: MutableList<RateModel> = ArrayList()

    //База данных
    private var dataBase: AppDatabase? = null

    //Интерфейс для запроса к базе данных
    private var rateDao: RateDao? = null

    //Переменная для отслеживания изменений курса.
    private var rateChange: MutableList<Double> = ArrayList()


    // В данном методе вызывается метод initRateList инициализируется база данных на измениния в
    //которой подписывается слушатель. Инициализируется класс Recycler. При первом запуске приложения
    // обязательно подключение интернета. При последующих возможна работа без интернета, Recycler будет
    //проинициализирован из базы данных предыдущим сохраненным значение курса валют.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.GONE
        initRateList()
        dataBase = AppDatabase.getAppDataBase(context = this)
        rateDao = dataBase?.rateDao()
        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (editTextNumber.text.toString() != "") {
                    recycler.adapter = Recycler(
                        listRate,
                        applicationContext,
                        editTextNumber.text.toString().toDouble(), rateChange
                    )
                } else {
                    recycler.adapter = Recycler(listRate, applicationContext, 0.0, rateChange)
                }
                recycler.adapter!!.notifyDataSetChanged()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        val observable = rateDao!!.getAll()
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(object :
            DisposableSubscriber<MutableList<RateModel>>() {
            override fun onComplete() {


                recycler.adapter!!.notifyDataSetChanged()

            }


            override fun onError(t: Throwable?) {

                runOnUiThread {
                    val toast = Toast.makeText(applicationContext, "$t", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onNext(t: MutableList<RateModel>?) {
                if (t == null) initRateList()
                else {
                    for (i in t) {
                        rateChange.add(i.previous.toDouble() - i.value.toDouble())

                    }
                    listRate = t
                }

                recycler.layoutManager = LinearLayoutManager(applicationContext)
                if (editTextNumber.text.toString() != "") {
                    recycler.adapter = Recycler(
                        listRate,
                        applicationContext,
                        editTextNumber.text.toString().toDouble(), rateChange
                    )
                } else {
                    recycler.adapter = Recycler(listRate, applicationContext, 0.0, rateChange)
                }
                recycler.adapter!!.notifyDataSetChanged()

            }

        })


    }

    // Метод для заполнения базы данных и списка объектов в массив поставляемый в RecyclerView.
// При вызове метода запускается прогресс бар, на объект observable полученный из RateController и
// приведённый к Flowable подписывается слушатель. Данные обновляются каждую минуту.
    private fun initRateList() {
        progressBar.visibility = View.VISIBLE
        val observable = rateController.getRateAsync()
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).toFlowable(BackpressureStrategy.BUFFER)
            .repeatWhen { it.delay(60, TimeUnit.SECONDS) }
            .subscribe(object :
                DisposableSubscriber<Json4KotlinBase>() {
                override fun onComplete() {


                }

                override fun onNext(t: Json4KotlinBase?) {
                    if (t != null) {

                        for (i in t.valute.values.toMutableList()) {
                            rateChange.add(i.previous.toDouble() - i.value.toDouble())

                        }


                        listRate = t.valute.values.toMutableList()
                        rateDao?.updateData(listRate)
                        runOnUiThread { progressBar.visibility = View.GONE }
                    }


                }

                override fun onError(t: Throwable?) {
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        val toast =
                            Toast.makeText(applicationContext, "$t", Toast.LENGTH_LONG)
                        toast.show()
                    }

                }

            })

    }

}


