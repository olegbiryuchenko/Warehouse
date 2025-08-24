package com.biryuchenko.room.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.biryuchenko.room.dao.CategoryDao
import com.biryuchenko.room.dao.DocumentDao
import com.biryuchenko.room.dao.FolderDao
import com.biryuchenko.room.dao.ProductsDao
import com.biryuchenko.room.dao.ProductsDbDao
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.Folder
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductDb


@Database(
    entities = [(Category::class), (Document::class), (Product::class), (ProductDb::class),(Folder::class)],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)

    ]
)
internal abstract class ProductsDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun documentDao(): DocumentDao
    abstract fun productsDao(): ProductsDao
    abstract fun productsDbDao(): ProductsDbDao
    abstract fun folderDao(): FolderDao

}