package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.ProductsDbDao
import com.biryuchenko.room.entities.ProductDb
import com.biryuchenko.room.entities.ProductWithCategory
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineProductsDbRepository @Inject constructor(
    private val productsDbDao: ProductsDbDao
)  : ProductsDbRepository {
    override fun getAllItemsStream(): Flow<List<ProductWithCategory>> {
        return productsDbDao.getAll()
    }

    override suspend fun getItemStream(barcode: String): ProductWithCategory?{
        return productsDbDao.getProduct(barcode)
    }

    override suspend fun insertCategory(productDb: ProductDb) {
        return productsDbDao.insertAll(productDb)
    }

    override suspend fun deleteItem(productDb: ProductDb) {
        return productsDbDao.delete(productDb)
    }
}