package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.ProductsDao
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.repository.interfaces.ProductsRepository
import kotlinx.coroutines.flow.Flow

class OfflineProductsRepository(val productsDao: ProductsDao): ProductsRepository {
    override fun getAllItemsStream(docId: Long): Flow<List<Product>> {
        return productsDao.getAll(docId)
    }

    override suspend fun insertCategory(category: Product) {
        return productsDao.insertAll(category)
    }

    override suspend fun deleteItem(category: Product) {
        return productsDao.delete(category)
    }
}