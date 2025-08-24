package com.biryuchenko.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.FolderWithDocument
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    @Query("SELECT * FROM documents")
    fun getAll(): Flow<List<Document>>

    @Query("SELECT * FROM documents WHERE folder_id = :folderId")
    fun get(folderId: Long): Flow<List<FolderWithDocument>>

    @Query("SELECT * FROM documents WHERE date >= :firstDate AND date <= :lastDate ")
    fun filterByDate(firstDate: Long, lastDate: Long): Flow<List<Document>>

    @Query("SELECT * FROM documents WHERE date >= :firstDate AND date < :firstDate + 86400000")
    fun filterByOneDate(firstDate: Long): Flow<List<Document>>

    @Query("SELECT * FROM documents WHERE uid == :uid")
    fun findByName(uid: Long): Flow<Document?>

    @Insert
    suspend fun insertAll(vararg document: Document)

    @Delete
    suspend fun delete(document: Document)
}