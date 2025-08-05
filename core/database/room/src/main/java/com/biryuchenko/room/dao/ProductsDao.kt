package com.biryuchenko.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.biryuchenko.room.entities.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductsDao {
    @Query("SELECT * FROM products WHERE documentId == :docId")
    fun getAll(docId: Long):  Flow<List<Product>>

    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(product: Product)
}