package com.biryuchenko.documents.document

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.calculate.Calculator
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductWithCategory
import com.biryuchenko.room.entities.ProductWithDocument
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import com.biryuchenko.room.repository.interfaces.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentDbVm @Inject constructor(
    private val products: ProductsRepository,
    private val productsDb: ProductsDbRepository,
) : ViewModel() {

    var name by mutableStateOf("")
    var category by mutableStateOf("Unspecified")
    var categoryId by mutableLongStateOf(0)
    var documentId by mutableLongStateOf(0)
    val allProducts: StateFlow<List<ProductWithDocument>> =
        products.getProductsWithDocuments(documentId = documentId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun findByBarcode(barcode: String): Flow<ProductWithCategory?> {
        return productsDb.getItemStream(barcode)
    }


    fun add() {

        viewModelScope.launch {
            try {
                products.insertProduct(
                    Product(
                        category = category,
                        name = name,
                        price = price.toInt(),
                        priceForOne = outPriceForOneItem.toInt(),
                        documentId = categoryId,
                        quantity = quantity.toInt(),
                    )
                )
            } catch (e: Exception) {

            }
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch {
            try {
                products.deleteItem(product)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    var result: String? = ""

    private var calculator = Calculator()
    private var output = Pair(0, 0)
    var outPrice by mutableStateOf("")
    var outPriceForOneItem by mutableStateOf("")
    var price by mutableStateOf("")
    var percent by mutableStateOf("")
    var quantity by mutableStateOf("")


    suspend fun calc() {
        delay(300)
        if (
            price.toDoubleOrNull() == null ||
            percent.toIntOrNull() == null ||
            quantity.toIntOrNull() == null
        ) {
            return
        }
        output = calculator.calc(
            price.toDouble(),
            quantity.toInt(),
            percent.toInt()
        )
        outPrice = output.second.toString()
        outPriceForOneItem = output.first.toString()
    }

    fun calculateTotalAmount() {
        if (
            outPriceForOneItem.toDoubleOrNull() == null ||
            quantity.toIntOrNull() == null
        ) {
            return
        }
        val price =
            calculator.calculateTotalAmount(outPriceForOneItem.toDouble(), quantity.toInt())
                .toString()
        outPrice = price
    }

    fun calculatePricePerUnit() {
        if (
            outPrice.toDoubleOrNull() == null ||
            quantity.toIntOrNull() == null
        ) {
            return
        }
        val price =
            calculator.calculatePricePerUnit(outPrice.toDouble(), quantity.toInt()).toString()
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