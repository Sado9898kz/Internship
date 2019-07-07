package com.example.bbcnews.Controller

import java.util.Date
import java.text.SimpleDateFormat
import java.util.*
import com.example.bbcnews.Controller.WeatherItem as WeatherItem1

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "EEE, dd MMM"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.time(hour: Int, minut: Int): Date {

    val time = Date(year, month, date, hour, minut, seconds)

    return time
}

fun Date.add(value: Int, units: TimeUnits): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}


fun Date.humanizeDate(date: Date = Date()): String {

    var message = ""
    val time = this.time - date.time

    if (time + 1 * DAY <= 1 * DAY) {
        message = "Сегодня"
    } else if (time < 1 * DAY) {
        message = "Завтра"
    }

    return message
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}
