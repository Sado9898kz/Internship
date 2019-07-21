package com.example.bbcnews

import android.util.Log
import com.example.bbcnews.Controller.*
import com.example.bbcnews.Model.GPS
import com.example.bbcnews.Model.createRequest
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_date() {
        println(Date().humanizeDate())// Сегодня
        println(Date().add(1, TimeUnits.DAY).humanizeDate())// Сегодня
        println(Date().add(2, TimeUnits.DAY).humanizeDate())// Завтра
        println(Date().add(3, TimeUnits.DAY).humanizeDate())// Не должна работать
        println(Date().format())// Дата
    }

    @Test
    fun test_time() {
        val begin = Date().time(20, 0)
        val end = Date().time(6, 0)
        val time = Date().add(10, TimeUnits.HOUR)// Добавлем 10 часов
        if (time > begin || time < end) {
            println("Ok $time")
        } else {
            println("Error $time")
        }
    }
/*
    @Test
    fun ff() {

        val Latitude="42.8683282"
        val Longitude="74.6342356"

        class KeyGPS(
                val Key:String)

        fun funGPS(latitude: String, longitude: String) {
            val o =
                    createRequest("http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=jGUV6V6BSzwPAp26Q8KeufouhhlBEhNU%20&q=$latitude%2C-$longitude")
                            .map { Gson().fromJson(it, KeyGPS::class.java) }
                            .subscribeOn(Schedulers.io())

            GPS = o.subscribe({
                it.Key
                println("${it.Key} Connect GPS")
            }, {
                println("Connect Dialog Search")
            })
        }

        funGPS(Latitude,Longitude)
    }*/

}
