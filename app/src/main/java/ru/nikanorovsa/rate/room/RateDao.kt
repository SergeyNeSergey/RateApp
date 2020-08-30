package ru.nikanorovsa.rate.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nikanorovsa.rate.model.RateModel
import rx.Observable

@Dao
interface RateDao {

    @Query("SELECT * FROM rate_model")
    fun getAll(): Observable<RateModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(rateModelList: List<RateModel>)


    @Query("SELECT * FROM rate_model WHERE id LIKE :id")
    fun findById(id: String): Observable<RateModel>

    @Query("DELETE FROM rate_model")
    fun deleteAllTable()
}