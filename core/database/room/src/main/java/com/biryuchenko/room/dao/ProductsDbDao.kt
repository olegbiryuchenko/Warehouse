package com.biryuchenko.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.biryuchenko.room.entities.ProductDb
import com.biryuchenko.room.entities.ProductWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDbDao {
    @Query("SELECT * FROM productsDb")
    fun getAll(): Flow<List<ProductWithCategory>>

    @Query("SELECT * FROM productsDb WHERE barcode == :barcode")
    fun getProduct(barcode: String): Flow<ProductWithCategory?>

    @Insert
    suspend fun insertAll(vararg productDb: ProductDb)

    @Delete
    suspend fun delete(productDb: ProductDb)
}