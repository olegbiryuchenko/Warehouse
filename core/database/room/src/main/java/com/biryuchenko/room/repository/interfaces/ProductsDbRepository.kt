package com.biryuchenko.room.repository.interfaces

import com.biryuchenko.room.entities.ProductDb
import com.biryuchenko.room.entities.ProductWithCategory
import kotlinx.coroutines.flow.Flow

interface ProductsDbRepository {
    fun getAllItemsStream(): Flow<List<ProductWithCategory>>

    suspend fun getItemStream(barcode: String): ProductWithCategory?

    suspend fun insertCategory(productDb: ProductDb)

    suspend fun deleteItem(productDb: ProductDb)
}