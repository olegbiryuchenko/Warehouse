package com.biryuchenko.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.biryuchenko.room.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao{
    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<Category>>


    @Query("SELECT * FROM categories WHERE category == :category")
    fun findByName(category: String,):Flow<Category>

    @Insert
    suspend fun insertAll(vararg category: Category)

    @Delete
    suspend  fun delete(category: Category)
}
