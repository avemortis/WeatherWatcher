package com.weather.di

import android.location.Location
import com.weather.data.network.WeatherService
import com.weather.utils.WeatherFragmentFactory
import dagger.Module
import dagger.Provides

@Module
class WeatherServiceModule(private val location: Location) {
    private val service = WeatherService()

    @WeatherScope
    @Provides
    fun provideWeatherService() = service

    @WeatherScope
    @Provides
    fun provideFragmentFactory() = WeatherFragmentFactory(service, location)
}