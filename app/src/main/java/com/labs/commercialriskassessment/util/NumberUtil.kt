package com.labs.commercialriskassessment.util

import kotlin.math.pow
import kotlin.math.round

class NumberUtil(private val digits: Int) {

    fun roundTo(number: Double):Double{
        val digitsRound: Double = 10.0.pow(digits)
        return round(number * digitsRound)/digitsRound
    }

}