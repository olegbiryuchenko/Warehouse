package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.CategoryDao
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.repository.interfaces.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getAllItemsStream(): Flow<List<Category>> {
        return categoryDao.getAll()
    }

    override fun getItemStream(name: String): Flow<Category?> {
        return categoryDao.findByName(name)
    }

    override suspend fun insertCategory(category: Category) {
        return categoryDao.insertAll(category)
    }

    override suspend fun deleteItem(category: Category) {
        return categoryDao.delete(category)
    }
}