package com.weather.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.R
import com.weather.ui.recyclerview.WeatherWeekOverviewAdapter

class WeatherWeekOverviewComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val view: View = LayoutInflater
        .from(context)
        .inflate(
            R.layout.component_weather_week_overview,
            this,
            true
        )
    val list : RecyclerView = view.findViewById<RecyclerView?>(R.id.weather_week_overview_recyclerview).apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}