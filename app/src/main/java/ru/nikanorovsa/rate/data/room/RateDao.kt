package ru.nikanorovsa.rate.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Query("SELECT * FROM rate_model")
    fun getAll(): Flow<List<RateModel>>

    @Insert
    suspend fun insertAll(rateModelList: List<RateModel>)

    @Query("DELETE FROM rate_model")
    suspend fun deleteAllTable()

    @Transaction
    suspend fun updateData(rate: List<RateModel>) {
        deleteAllTable()
        insertAll(rate)
    }
}