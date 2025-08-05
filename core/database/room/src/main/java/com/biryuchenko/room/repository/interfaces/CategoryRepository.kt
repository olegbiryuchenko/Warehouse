package com.biryuchenko.room.repository.interfaces

import com.biryuchenko.room.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllItemsStream(): Flow<List<Category>>

    fun getItemStream(name: String): Flow<Category?>

    suspend fun insertCategory(category: Category)

    suspend fun deleteItem(category: Category)
}
