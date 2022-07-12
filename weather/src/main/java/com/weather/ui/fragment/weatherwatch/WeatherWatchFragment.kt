package com.weather.ui.fragment.weatherwatch

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.R
import com.weather.data.network.WeatherClient
import com.weather.databinding.FragmentWeatherWatchBinding
import com.weather.model.WeatherCompleteModel
import com.weather.ui.recyclerview.WeatherWeekOverviewAdapter
import com.weather.utils.weekDayAndDayOfMonthFromTimestamp
import com.weather.utils.formatLocationWeatherInformationString
import com.weather.utils.formatTemp

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
        getData()
        return bind.root
    }

    override fun onDestroy() {
        viewModel.disposable.dispose()
        super.onDestroy()
    }

    private fun getData() {
        viewModel.source.let { source ->
            if (source.value == null) source.subscribe { setWeather(it) } else setWeather(source.value!!)
        }
    }

    private fun setWeather(weather: WeatherCompleteModel) {
        bind.weatherWeekProgressbar.visibility = View.INVISIBLE
        bind.weatherWeekOverviewComponent.list?.adapter = WeatherWeekOverviewAdapter(
            weather.daily.size,
            onBind = { holder, position ->
                weekDayAndDayOfMonthFromTimestamp(onData = { weekDay, dayOfMonth ->
                    holder.weekDayTextView.text = weekDay
                    holder.monthDayNumber.text = dayOfMonth.toString()
                }, weather.daily[position].dt)
                holder.temperatureTextView.text = formatTemp(weather.daily[position].temp.day)
            }
        )
        bind.weatherDayOverviewComponent.apply {
            topTextView.text =
                formatLocationWeatherInformationString(weather.current.temp, weather.timezone)
            bottomTextView.text =
                getString(R.string.feels_like, formatTemp(weather.current.feelsLike))
        }
    }
}