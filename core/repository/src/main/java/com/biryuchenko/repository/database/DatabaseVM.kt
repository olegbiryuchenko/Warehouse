package com.biryuchenko.repository.database

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class DatabaseVM @Inject constructor(

) : ViewModel() {
    // Тепер ви можете використовувати productsDatabase для отримання DAO
    // та виконання операцій з базою даних.
    // Наприклад:
    // private val productDao = productsDatabase.productDao()

    fun addNewProduct(productName: String) {
        // ... ваш код для додавання товару
    }
}