package ru.nikanorovsa.rate.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rate_model")
data class RateModel(
    @PrimaryKey var id: String,
    var num_code: Int,
    var char_code: String,
    var nominal: Int,
    var name: String,
    var value: String,
    var previous: String
)