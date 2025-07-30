package com.biryuchenko.documents

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.biryuchenko.calculate.Calculator
import kotlinx.coroutines.delay


class DocumentViewModel : ViewModel() {

    private var calculator = Calculator()
    private var output = Pair(0, 0)
    var outPrice by mutableStateOf("")
    var outPriceForOneItem by mutableStateOf("")
    var price by mutableStateOf("")
    var percent by mutableStateOf("")
    var count by mutableStateOf("")


    suspend fun calc() {
        delay(300)
        if (
            price.toDoubleOrNull() == null ||
            percent.toDoubleOrNull() == null ||
            count.toDoubleOrNull() == null
        ) {
            return
        }

        output = calculator.calc(
            price.toDouble(),
            count.toInt(),
            percent.toInt()
        )
        outPrice = output.second.toString()
        outPriceForOneItem = output.first.toString()
    }

    fun calculateTotalAmount() {
        if (
            outPriceForOneItem.toDoubleOrNull() == null ||
            count.toDoubleOrNull() == null
        ) {
            return
        }
        val price =
            calculator.calculateTotalAmount(outPriceForOneItem.toDouble(), count.toInt()).toString()
        outPrice = price
    }

    fun calculatePricePerUnit() {
        if (
            outPrice.toDoubleOrNull() == null ||
            count.toDoubleOrNull() == null
        ) {
            return
        }
        val price = calculator.calculatePricePerUnit(outPrice.toDouble(), count.toInt()).toString()
        outPriceForOneItem = price
    }

    fun filter(txt: String): String {
        val cleanedText = txt.replace(',', '.')


        val parts = cleanedText.split('.')
        val finalTxt = if (parts.size > 2) {

            parts[0] + "." + parts.subList(1, parts.size).joinToString("").filter { it.isDigit() }
        } else {

            cleanedText.filter { it.isDigit() || it == '.' }
        }
        return finalTxt
    }
}