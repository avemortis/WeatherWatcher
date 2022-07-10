package com.weather.ui.fragment.weatherwatch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.R

class WeatherWatchFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherWatchFragment()
    }

    private lateinit var viewModel: WeatherWatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_watch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherWatchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}