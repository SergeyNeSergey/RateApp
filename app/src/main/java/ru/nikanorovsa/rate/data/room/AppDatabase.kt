package ru.nikanorovsa.rate.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RateModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao
}