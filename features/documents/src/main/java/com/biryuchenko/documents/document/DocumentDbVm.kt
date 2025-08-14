package com.biryuchenko.documents.document

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.calculate.Calculator
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductWithDocument
import com.biryuchenko.room.repository.interfaces.CategoryRepository
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import com.biryuchenko.room.repository.interfaces.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentDbVm @Inject constructor(
    private val products: ProductsRepository,
    private val productsDb: ProductsDbRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    var name by mutableStateOf("")
    var category by mutableStateOf("")

    private val documentId = MutableStateFlow<Long?>(null)

    fun setDocumentId(id: Long) {
        documentId.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val allProducts: StateFlow<List<ProductWithDocument>> =
        documentId.filterNotNull().flatMapLatest { id ->
            products.getProductsWithDocuments(documentId = id)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    var categoryId by mutableLongStateOf(0)
    val allCategories: StateFlow<List<Category>> =
        categoryRepository.getAllItemsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun findByBarcode(barcode: String, context: Context) {
        viewModelScope.launch {
            try {
                val productWithCategory = productsDb.getItemStream(barcode)

                if (productWithCategory != null) {

                    category = productWithCategory.categoryDetails.category
                    percent = productWithCategory.categoryDetails.percent.toString()
                    name = productWithCategory.product.name
                } else {
                    category = "Unspecified"
                    percent = ""
                    Toast.makeText(
                        context,
                        "Штрих-код не найден или неправильно считан",
                        Toast.LENGTH_SHORT
                    ).show()
                    println("Product with barcode $barcode not found.")
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    e.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("findByBarcode", "An error occurred: ${e.message}", e)
                category = "Unspecified"
            }
        }
    }

    val totalPrice: StateFlow<Int> = allProducts.map { products ->
        products.sumOf { it.product.price }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )

    suspend fun add(): Boolean {
        if (
            documentId.value == null || name.isEmpty() || category.isEmpty()
            || outPrice.isEmpty() || outPriceForOneItem.isEmpty() || quantity.isEmpty()
        ) {
            return false
        }

        return try {
            products.insertProduct(
                Product(
                    category = category,
                    name = name,
                    price = outPrice.toInt(),
                    priceForOne = outPriceForOneItem.toInt(),
                    documentId = documentId.value!!,
                    quantity = quantity.toInt(),
                )
            )
            true
        } catch (e: Exception) {

            Log.e("DocumentDbVm", "Failed to add product: ${e.message}", e)
            false
        }
    }

    fun delete(product: Product, context: Context) {
        viewModelScope.launch {
            try {
                products.deleteItem(product)
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
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
        if (price.toDoubleOrNull() == null || percent.toIntOrNull() == null || quantity.toIntOrNull() == null) {
            return
        }
        output = calculator.calc(
            price.toDouble(), quantity.toInt(), percent.toInt()
        )
        outPrice = output.second.toString()
        outPriceForOneItem = output.first.toString()
    }

    fun calculateTotalAmount() {
        if (outPriceForOneItem.toDoubleOrNull() == null || quantity.toIntOrNull() == null) {
            return
        }
        val price = calculator.calculateTotalAmount(outPriceForOneItem.toDouble(), quantity.toInt())
            .toString()
        outPrice = price
    }

    fun calculatePricePerUnit() {
        if (outPrice.toDoubleOrNull() == null || quantity.toIntOrNull() == null) {
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