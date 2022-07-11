package com.weather.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class WeatherService @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val service = retrofit.create<WeatherClient>()

    private companion object {
        private const val WEATHER_BASE_URL = "https://api.openweathermap.org/"
    }
}