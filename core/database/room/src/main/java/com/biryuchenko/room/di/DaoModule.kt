package com.biryuchenko.room.di

import com.biryuchenko.room.dao.CategoryDao
import com.biryuchenko.room.dao.DocumentDao
import com.biryuchenko.room.dao.FolderDao
import com.biryuchenko.room.dao.ProductsDao
import com.biryuchenko.room.dao.ProductsDbDao
import com.biryuchenko.room.database.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    @Singleton
    fun provideCategoryDao(database: ProductsDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun providesDocumentDao(
        database: ProductsDatabase,
    ): DocumentDao = database.documentDao()

    @Provides
    @Singleton
    fun providesProductsDao(
        database: ProductsDatabase,
    ): ProductsDao = database.productsDao()

    @Provides
    @Singleton
    fun providesProductsDbDao(
        database: ProductsDatabase,
    ): ProductsDbDao{
        return database.productsDbDao()
    }
    @Provides
    @Singleton
    fun providesFolderDao(
        database: ProductsDatabase,
    ): FolderDao{
        return database.folderDao()
    }
}