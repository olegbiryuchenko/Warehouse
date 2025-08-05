package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.ProductsDbDao
import com.biryuchenko.room.entities.ProductDb
import com.biryuchenko.room.entities.ProductWithCategory
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import kotlinx.coroutines.flow.Flow

class OfflineProductsDbRepository(val productDbDao: ProductsDbDao) : ProductsDbRepository {
    override fun getAllItemsStream(): Flow<List<ProductDb>> {
        return productDbDao.getAll()
    }

    override fun getItemStream(barcode: Int): Flow<ProductWithCategory?> {
        return productDbDao.getProduct(barcode)
    }

    override suspend fun insertCategory(category: ProductDb) {
        return productDbDao.insertAll(category)
    }

    override suspend fun deleteItem(category: ProductDb) {
        return productDbDao.delete(category)
    }
}