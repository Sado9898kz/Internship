package com.example.bbcnews.Controller

import java.util.*
import kotlin.collections.ArrayList

class FeedAccuweatherAPI(
    val DailyForecasts: ArrayList<WeatherItemAPI>
)

class WeatherItemAPI(
    val Date: Date,
    val Temperature: Temperature,
    val Day: Day,
    val Night: Night,
    val MobileLink: String
)

class Day(
    val Icon: Int,
    val IconPhrase: String
)

class Night(
    val Icon: Int,
    val IconPhrase: String
)

class Temperature(
    val Minimum: Minimum,
    val Maximum: Maximum
)

class Minimum(
    val Value: String
)

class Maximum(
    val Value: String
)