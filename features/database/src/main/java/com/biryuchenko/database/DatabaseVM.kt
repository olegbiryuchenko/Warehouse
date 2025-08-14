package com.biryuchenko.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.entities.ProductDb
import com.biryuchenko.room.entities.ProductWithCategory
import com.biryuchenko.room.repository.interfaces.CategoryRepository
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import com.biryuchenko.room.repository.interfaces.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseVM @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val databaseRepository: ProductsDbRepository
) : ViewModel() {

    var name by mutableStateOf("")
    var category by mutableStateOf("")
    var categoryId by mutableLongStateOf(0)
        val allCategories: StateFlow<List<Category>> =
            categoryRepository.getAllItemsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    val allProducts: StateFlow<List<ProductWithCategory>> =
        databaseRepository.getAllItemsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    suspend fun inspect(barcode: String): Boolean {

        val existingProduct = databaseRepository.getItemStream(barcode)?.product?.barcode?.isEmpty()

        return existingProduct == null
    }
    fun add(barcode: String,context: Context) {
        viewModelScope.launch {
              try {
                  databaseRepository.insertCategory(
                      ProductDb(
                          name = name,
                          barcode = barcode,
                          categoryId = categoryId
                      )
                  )
              } catch (e: Exception) {
                  Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                  Log.e("DatabaseError", "Failed to insert product", e)
              }
        }
    }

    fun delete(productDb: ProductDb) {
        viewModelScope.launch {
            try {
                databaseRepository.deleteItem(
                    productDb
                )
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}