package com.weather.data.network

import com.weather.model.WeatherCompleteModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherClient {
    @GET("/data/2.5/onecall")
    fun getWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "ru",
        @Query("appid") appid: String = "d3783d35cd85c8185743026665074d77"
    ): Single<WeatherCompleteModel>
}