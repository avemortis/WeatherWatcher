package com.weather.utils

import android.location.Location
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.weather.data.network.WeatherService
import com.weather.ui.fragment.weatherwatch.WeatherWatchFragment

class WeatherFragmentFactory(val service: WeatherService, val location: Location) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        if (className == WeatherWatchFragment::class.java.name) {
            return WeatherWatchFragment(location, service)
        }
        return super.instantiate(classLoader, className)
    }
}