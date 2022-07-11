package com.weather.ui.fragment.weatherwatch

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.data.network.WeatherClient
import com.weather.databinding.FragmentWeatherWatchBinding
import com.weather.model.WeatherCompleteModel
import com.weather.ui.recyclerview.WeatherWeekOverviewAdapter

class WeatherWatchFragment(
    location: Location,
    service: WeatherClient
) : Fragment() {
    private val modelFactory = WeatherWatchViewModelFactory(service, location)
    private lateinit var viewModel: WeatherWatchViewModel
    private lateinit var bind: FragmentWeatherWatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, modelFactory)[WeatherWatchViewModel::class.java]
        bind = FragmentWeatherWatchBinding.inflate(inflater, container, false)
        subscribe()
        return bind.root
    }

    override fun onDestroy() {
        viewModel.disposable.dispose()
        super.onDestroy()
    }

    private fun subscribe() {
        viewModel.source.let { source ->
            if (source.value == null) source.subscribe { setWeather(it) } else setWeather(source.value!!)
        }
    }

    private fun setWeather(weather: WeatherCompleteModel) {
        bind.weatherWeekOverviewComponent.list?.adapter = WeatherWeekOverviewAdapter(
            weather.daily.size,
            onBind = { holder, position ->
                holder.weekDayTextView.text = position.toString()
                holder.temperatureTextView.text =
                    weather.daily[position].temp.day.toString()
            }
        )
        bind.weatherDayOverviewComponent.topTextView.text = weather.timezone
    }
}