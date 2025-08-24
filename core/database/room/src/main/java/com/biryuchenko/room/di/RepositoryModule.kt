package com.biryuchenko.room.di

import com.biryuchenko.room.repository.classes.OfflineCategoryRepository
import com.biryuchenko.room.repository.classes.OfflineDocumentRepository
import com.biryuchenko.room.repository.classes.OfflineFolderRepository
import com.biryuchenko.room.repository.classes.OfflineProductsDbRepository
import com.biryuchenko.room.repository.classes.OfflineProductsRepository
import com.biryuchenko.room.repository.interfaces.CategoryRepository
import com.biryuchenko.room.repository.interfaces.DocumentRepository
import com.biryuchenko.room.repository.interfaces.FolderRepository
import com.biryuchenko.room.repository.interfaces.ProductsDbRepository
import com.biryuchenko.room.repository.interfaces.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: OfflineCategoryRepository
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductsDbRepository(
        impl: OfflineProductsDbRepository
    ): ProductsDbRepository

    @Binds
    @Singleton
    abstract fun bindDocumentRepository(
        impl: OfflineDocumentRepository
    ): DocumentRepository

    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        impl: OfflineProductsRepository
    ): ProductsRepository

    @Binds
    @Singleton
    abstract fun bindFolderRepository(
        impl: OfflineFolderRepository
    ): FolderRepository
}

