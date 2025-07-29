package com.biryuchenko.calculate

import kotlin.math.ceil


class Calculator {

    public fun calc(
        priceIn: Double,
        count: Int,
        percent: Int,
    ): Pair<Int, Int> {
        val a = pricePerUnit(priceIn, count, percent)
        val b = price(a, count)
        return Pair(a, b)
    }

    internal fun pricePerUnit(
        price: Double,
        count: Int,
        percent: Int,
    ): Int {
        return try {
            if (count == 0) {
                return 0
            }
            val calculatedValue = ((price + (price * (percent) / 100)) / count)
            val ceiledValue = ceil(calculatedValue)
            return ceiledValue.toInt()
        } catch (e: ArithmeticException) {
            0
        }
    }

    internal fun price(
        price: Int,
        count: Int,
    ): Int {
        return try {
            if (count == 0) {
                return 0
            }
            price * count
        } catch (e: ArithmeticException) {
            0
        }
    }
}