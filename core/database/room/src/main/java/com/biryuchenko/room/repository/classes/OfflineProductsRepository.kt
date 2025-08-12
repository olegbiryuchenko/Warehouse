package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.ProductsDao
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductWithDocument
import com.biryuchenko.room.repository.interfaces.ProductsRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineProductsRepository @Inject constructor(
    private val productsDao: ProductsDao
) : ProductsRepository {


    override fun getAllItemsStream(): Flow<List<Product>> {
        return productsDao.getAll()
    }

    override fun getProductsWithDocuments(documentId: Long): Flow<List<ProductWithDocument>> {
        return productsDao.getProductsWithDocuments(documentId)
    }

    override suspend fun insertProduct(product: Product) {
        return productsDao.insertAll(product)
    }

    override suspend fun deleteItem(product: Product) {
        return productsDao.delete(product)
    }
}