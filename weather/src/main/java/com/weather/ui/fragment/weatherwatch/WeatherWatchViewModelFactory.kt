package com.weather.ui.fragment.weatherwatch

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.data.network.WeatherClient

class WeatherWatchViewModelFactory(private val client: WeatherClient, private val location: Location) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WeatherClient::class.java, Location::class.java)
            .newInstance(client, location)
    }
}