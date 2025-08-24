package com.biryuchenko.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.biryuchenko.room.entities.Folder
import kotlinx.coroutines.flow.Flow


@Dao
interface FolderDao {

    @Query("SELECT * FROM folders")
    fun getAll(): Flow<List<Folder>>

    @Insert
    suspend fun insertAll(vararg folder: Folder)

    @Delete
    suspend fun delete(folder: Folder)
}