package com.biryuchenko.room.repository.interfaces

import com.biryuchenko.room.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllItemsStream(docId: Long): Flow<List<Product>>

    suspend fun insertCategory(category: Product)

    suspend fun deleteItem(category: Product)

}