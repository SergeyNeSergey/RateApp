package ru.nikanorovsa.rate.data.retrofit

import retrofit2.http.GET

interface RateController {

    @GET("daily_json.js")
    suspend fun getRateAsync(): Json4KotlinBase
}