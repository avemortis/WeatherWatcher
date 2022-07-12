package com.weather.utils

import java.text.SimpleDateFormat
import java.util.*

fun weekDayAndDayOfMonthFromTimestamp(onData: (weekDay: String, dayOfMonth: Int) -> Unit, timeStamp: Long) {
    val calendar = Calendar.getInstance()
    val inMills = timeStamp * 1000
    calendar.timeInMillis = inMills

    val date = SimpleDateFormat("EEEE", Locale.ENGLISH)
    val weekDay = date.format(inMills).toString()
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    onData(weekDay, dayOfMonth)
}