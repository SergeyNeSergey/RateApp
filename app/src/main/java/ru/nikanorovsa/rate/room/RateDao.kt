package ru.nikanorovsa.rate.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import ru.nikanorovsa.rate.model.RateModel

// Интерфейс для обращения к базе данных Room
@Dao
interface RateDao {

    @Query("SELECT * FROM rate_model")
    fun getAll(): Flowable<MutableList<RateModel>>

    @Insert
    fun insertAll(rateModelList: MutableList<RateModel>)


    @Query("DELETE FROM rate_model")
    fun deleteAllTable()

    @Transaction
    fun updateData(rate: MutableList<RateModel>) {
        deleteAllTable()
        insertAll(rate)

    }
}