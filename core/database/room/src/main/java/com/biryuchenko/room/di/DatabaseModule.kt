package com.biryuchenko.room.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.biryuchenko.room.database.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesRoomDatabase(
        @ApplicationContext context: android.content.Context,
    ): ProductsDatabase = Room.databaseBuilder(
        context,
        ProductsDatabase::class.java,
        "items-database",
    ).build()

}
