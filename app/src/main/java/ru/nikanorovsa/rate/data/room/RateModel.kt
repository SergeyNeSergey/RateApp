package ru.nikanorovsa.rate.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rate_model")
data class RateModel(
     @SerializedName("ID") @PrimaryKey var id : String,
     @SerializedName("NumCode") var numCode : String,
     @SerializedName("CharCode") var charCode : String,
     @SerializedName("Nominal") var nominal : Int,
     @SerializedName("Name") var name : String,
     @SerializedName("Value") var value : Double,
     @SerializedName("Previous") var previous : Double
)