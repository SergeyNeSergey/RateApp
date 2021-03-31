package ru.nikanorovsa.rate.data.retrofit

import com.google.gson.annotations.SerializedName
import ru.nikanorovsa.rate.data.room.RateModel

data class Json4KotlinBase(

    @SerializedName("Date") val date: String,
    @SerializedName("PreviousDate") val previousDate: String,
    @SerializedName("PreviousURL") val previousURL: String,
    @SerializedName("Timestamp") val timestamp: String,
    @SerializedName("Valute") val valute: HashMap<String, RateModel>
)
