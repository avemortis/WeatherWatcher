package com.weather.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.weather.R

class WeatherDayOverviewComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val view: View = LayoutInflater
        .from(context)
        .inflate(
            R.layout.component_weather_day_overview,
            this,
            true
        )

    val topTextView : TextView = view.findViewById(R.id.weather_day_overview_top_textview)
    val bottomTextView : TextView = view.findViewById(R.id.weather_day_overview_bottom_textview)
}