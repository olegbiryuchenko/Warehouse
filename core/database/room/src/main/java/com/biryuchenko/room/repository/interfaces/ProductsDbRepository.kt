package com.biryuchenko.room.repository.interfaces

import com.biryuchenko.room.entities.ProductDb
import com.biryuchenko.room.entities.ProductWithCategory
import kotlinx.coroutines.flow.Flow

interface ProductsDbRepository {
    fun getAllItemsStream(): Flow<List<ProductDb>>

    fun getItemStream(barcode: Int): Flow<ProductWithCategory?>

    suspend fun insertCategory(category: ProductDb)

    suspend fun deleteItem(category: ProductDb)
}