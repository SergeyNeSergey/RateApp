package ru.nikanorovsa.rate.data.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.nikanorovsa.rate.data.room.RateModel

@Parcelize
data class Json4KotlinBase(
    val date: String,
    val previousDate: String,
    val previousURL: String,
    val timestamp: String,
    val valuta: HashMap<String, RateModel>
) : Parcelable
