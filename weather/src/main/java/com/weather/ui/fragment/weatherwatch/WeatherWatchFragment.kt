package com.weather.ui.fragment.weatherwatch

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.data.network.WeatherService
import com.weather.databinding.FragmentWeatherWatchBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherWatchFragment(
    val location: Location,
    val service: WeatherService
) : Fragment() {
    private lateinit var viewModel: WeatherWatchViewModel
    private lateinit var bind: FragmentWeatherWatchBinding

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[WeatherWatchViewModel::class.java]
        bind = FragmentWeatherWatchBinding.inflate(inflater, container, false)
        subscribe()
        return bind.root
    }

    private fun subscribe() {
        service.service.getWeather(location.latitude.toFloat(), location.longitude.toFloat())
            .observeOn(Schedulers.single())
            .subscribeOn(Schedulers.io())
            .subscribe { t1, _ ->
                Log.d("Lol", t1.toString())
            }
    }
}