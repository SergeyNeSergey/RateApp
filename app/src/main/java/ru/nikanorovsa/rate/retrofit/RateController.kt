package ru.nikanorovsa.rate.retrofit

import retrofit2.http.GET
import ru.nikanorovsa.rate.model.RateModel
import rx.Observable

/**
 * Контроллер для работы с api
 */
interface RateController {
    /**
     * Метод для получения валюты с сайта
     */
    @GET("Valute")
    fun getRateAsync(): Observable<List<RateModel>>
}