package com.weather.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.databinding.HolderWeatherWeekItemBinding

class WeatherWeekOverviewAdapter(
    var size: Int,
    val onBind: (holder: WeatherWeekOverviewViewHolder, position: Int) -> Unit
) : RecyclerView.Adapter<WeatherWeekOverviewViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherWeekOverviewViewHolder {
        val bind =
            HolderWeatherWeekItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherWeekOverviewViewHolder(bind)
    }

    override fun onBindViewHolder(holder: WeatherWeekOverviewViewHolder, position: Int) {
        onBind(holder, position)
    }

    override fun getItemCount() = size
}

class WeatherWeekOverviewViewHolder(binding: HolderWeatherWeekItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val weekDayTextView = binding.holderWeatherWeekDayLabel
    val temperatureTextView = binding.holderWeatherWeekTemperature
    val monthDayNumber = binding.holderWeatherWeekDayNumber
}