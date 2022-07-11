package com.weather.di

import com.weather.data.network.WeatherService
import com.weather.utils.WeatherFragmentFactory
import dagger.Component

@WeatherScope
@Component(modules = [WeatherServiceModule::class])
interface WeatherComponent {
    fun getWeatherService(): WeatherService
    fun getFragmentFactory(): WeatherFragmentFactory
}