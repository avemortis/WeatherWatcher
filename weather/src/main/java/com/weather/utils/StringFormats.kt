package com.weather.utils

fun formatLocationWeatherInformationString(temp: Float, countryAndLocationLabel: String) = run {
    val builder = StringBuilder()
    builder.append(formatTemp(temp))
    builder.append(countryAndLocationLabel.dropWhile { char ->
        char != '/'
    }.drop(1))
    builder.toString()
}

fun formatTemp(temp: Float) = run {
    when {
        temp < 0f -> "- $temp ° "
        temp > 0f -> "+ $temp ° "
        else -> "$temp ° "
    }
}