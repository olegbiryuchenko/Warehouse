package com.biryuchenko.room.repository.interfaces


import com.biryuchenko.room.entities.Document
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getAllItemsStream(): Flow<List<Document>>

    fun getItemStream(uid: Long): Flow<Document?>

    suspend fun insertCategory(category: Document)

    suspend fun deleteItem(category: Document)

}