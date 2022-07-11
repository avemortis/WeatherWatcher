package com.weather.ui.fragment.weatherwatch

import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.data.network.WeatherClient
import com.weather.data.network.WeatherService
import com.weather.databinding.FragmentWeatherWatchBinding
import com.weather.ui.recyclerview.WeatherWeekOverviewAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherWatchFragment(
    val location: Location,
    val service: WeatherClient
) : Fragment() {
    private lateinit var viewModel: WeatherWatchViewModel
    private lateinit var bind: FragmentWeatherWatchBinding

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
        service.getWeather(location.latitude.toFloat(), location.longitude.toFloat())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { t1, _ ->
                bind.weatherWeekOverviewComponent.list?.adapter = WeatherWeekOverviewAdapter(
                    t1.daily.size,
                    onBind = { holder, position ->
                        holder.weekDayTextView.text = position.toString()
                        holder.temperatureTextView.text = t1.daily[position].temp.day.toString()
                    }
                )
                bind.weatherDayOverviewComponent.topTextView.text = t1.timezone
            }
    }
}