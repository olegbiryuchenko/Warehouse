package com.biryuchenko.repository.database

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.biryuchenko.room.database.ProductsDatabase
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.repository.classes.OfflineCategoryRepository
import com.biryuchenko.room.repository.classes.OfflineDocumentRepository
import com.biryuchenko.room.repository.classes.OfflineProductsDbRepository
import com.biryuchenko.room.repository.classes.OfflineProductsRepository
import com.biryuchenko.room.repository.interfaces.CategoryRepository
import com.biryuchenko.room.repository.interfaces.DocumentRepository
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import com.biryuchenko.room.repository.interfaces.ProductsRepository

class DataBaseVM(context: Context) : ViewModel() {

    val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(ProductsDatabase.getDatabase(context).categoryDao())
    }
    val documentRepository: DocumentRepository by lazy {
        OfflineDocumentRepository(ProductsDatabase.getDatabase(context).documentDao())
    }
    val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(ProductsDatabase.getDatabase(context).productsDao())
    }
    val productsDbRepository: ProductsDbRepository by lazy {
        OfflineProductsDbRepository(ProductsDatabase.getDatabase(context).productsDbDao())
    }

    suspend fun addCategory(name: String, percent: Int, context: Context) {
        try {
            categoryRepository.insertCategory(Category(0, name, percent))
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}