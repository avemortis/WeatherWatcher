package com.weather.di

import com.weather.data.network.WeatherService
import dagger.Module
import dagger.Provides

@Module
class WeatherServiceModule {
    @WeatherScope
    @Provides
    fun provideWeatherService() = WeatherService()
}
