package com.biryuchenko.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.biryuchenko.room.dao.CategoryDao
import com.biryuchenko.room.dao.DocumentDao
import com.biryuchenko.room.dao.ProductsDao
import com.biryuchenko.room.dao.ProductsDbDao
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.Product
import com.biryuchenko.room.entities.ProductDb


@Database(
    entities = [(Category::class), (Document::class), (Product::class), (ProductDb::class)],
    version = 1
)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun documentDao(): DocumentDao
    abstract fun productsDao(): ProductsDao
    abstract fun productsDbDao(): ProductsDbDao


    companion object {

        @Volatile
        private var Instance: ProductsDatabase? = null
        fun getDatabase(context: Context): ProductsDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ProductsDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}