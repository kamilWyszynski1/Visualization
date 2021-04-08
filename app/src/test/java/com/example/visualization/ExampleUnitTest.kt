package com.example.visualization

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.pow

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
    fun sqrt_isCorrect() {
       assertEquals(5.0, 3.toDouble().pow(2).plus(4.toDouble().pow(2)).pow(0.5), 0.1)
    }
}