package ru.nikanorovsa.rate.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nikanorovsa.rate.model.RateModel

@Database(entities = [RateModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateDao(): RateDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "rate_model"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}