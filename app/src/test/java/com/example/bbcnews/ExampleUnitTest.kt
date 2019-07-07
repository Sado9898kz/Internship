package com.example.bbcnews

import com.example.bbcnews.Controller.*
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
}
