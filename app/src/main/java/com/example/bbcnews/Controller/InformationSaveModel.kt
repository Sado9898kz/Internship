package com.example.bbcnews.Controller

import io.realm.RealmList
import io.realm.RealmObject
import java.util.*

open class FeedAccuweather(
    var DailyForecasts: RealmList<WeatherItem> = RealmList<WeatherItem>()
) : RealmObject()

open class WeatherItem(
    var Date: Date = Date(),
    var ValMin: String = " ",
    var ValMax: String = " ",
    var IconDay: Int = 0,
    var IconNight: Int = 0,
    var DayIconPhrase: String = " ",
    var NightIconPhrase: String = " ",
    var MobileLink: String = " "
) : RealmObject()