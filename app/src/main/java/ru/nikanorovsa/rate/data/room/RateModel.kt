package ru.nikanorovsa.rate.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "rate_model")
@Parcelize
data class RateModel(
     @PrimaryKey val id: String,
     val num_code: Int,
     val char_code: String,
     val nominal: Int,
     val name: String,
     val value: String,
     val previous: String
) : Parcelable