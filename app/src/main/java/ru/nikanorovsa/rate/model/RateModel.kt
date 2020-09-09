package ru.nikanorovsa.rate.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName

// Класс для сохранения данных в базу данных SQL с помощью Room. Является составляющей
// @SerializedName("Valute") val valute : HashMap<String, RateModel> из класса Json4KotlinBase.
@Entity(tableName = "rate_model")
data class RateModel(
    @SerializedName("ID") @PrimaryKey var id: String,
    @SerializedName("NumCode") var num_code: Int,
    @SerializedName("CharCode") var char_code: String,
    @SerializedName("Nominal") var nominal: Int,
    @SerializedName("Name") var name: String,
    @SerializedName("Value") var value: String,
    @SerializedName("Previous") var previous: String
)