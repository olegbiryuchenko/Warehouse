package com.biryuchenko.room.repository.interfaces

import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductWithDocument
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllItemsStream(): Flow<List<Product>>

    fun getProductsWithDocuments(documentId:Long): Flow<List<ProductWithDocument>>

    suspend fun insertProduct(product: Product)

    suspend fun deleteItem(product: Product)

}