package ru.nikanorovsa.rate.model

import com.google.gson.annotations.SerializedName

//Класс POJO для получения данных из JSON через Retrofit
data class Json4KotlinBase(

    @SerializedName("Date") val date: String,
    @SerializedName("PreviousDate") val previousDate: String,
    @SerializedName("PreviousURL") val previousURL: String,
    @SerializedName("Timestamp") val timestamp: String,
    @SerializedName("Valute") val valute: HashMap<String, RateModel>
)
