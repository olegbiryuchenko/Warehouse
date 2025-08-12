package com.biryuchenko.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductWithDocument
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductsDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE documentId = :documentId")
    fun getProductsWithDocuments(documentId: Long): Flow<List<ProductWithDocument>>

    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(product: Product)
}