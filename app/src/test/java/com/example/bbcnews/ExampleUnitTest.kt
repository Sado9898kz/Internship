package com.example.bbcnews

import com.example.bbcnews.Controller.TimeUnits
import com.example.bbcnews.Controller.add
import com.example.bbcnews.Controller.humanizeDiff
import org.junit.Test

import org.junit.Assert.*
import java.util.Date

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
    fun test_image_ches() {
        println(Date().add(1,TimeUnits.DAY).humanizeDiff())// Сегодня
        println(Date().add(2,TimeUnits.DAY).humanizeDiff())// Завтра
    }

}
