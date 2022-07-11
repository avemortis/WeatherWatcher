package com.weather.model

import com.google.gson.annotations.SerializedName

data class CompleteWeatherModel(
    @SerializedName("timezone") val timezone: String,
    @SerializedName("current") val current: CurrentWeatherModel,
    @SerializedName("daily") val daily: List<WeatherByDay>
)

data class WeatherByDay(
    @SerializedName("temp") val temp: TemperatureAccurate,
    @SerializedName("feels_like") val feelsLike: TemperatureFeelsLike
)

data class TemperatureAccurate(
    @SerializedName("min") val min: Float,
    @SerializedName("max") val max: Float,
    @SerializedName("day") val day: Float,
    @SerializedName("night") val night: Float,
    @SerializedName("eve") val evening: Float,
    @SerializedName("morn") val morning: Float
)

data class TemperatureFeelsLike(
    @SerializedName("day") val day: Float,
    @SerializedName("night") val night: Float,
    @SerializedName("eve") val evening: Float,
    @SerializedName("morn") val morning: Float
)

data class CurrentWeatherModel(
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feelsLike: Float
)
