package ru.nikanorovsa.rate.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import ru.nikanorovsa.rate.model.Json4KotlinBase

/**
 * Контроллер для работы с api
 */
interface RateController {
    /**
     * Метод для получения валюты с сайта
     */
    @GET("daily_json.js")
    fun getRateAsync(): Observable<Json4KotlinBase>
}