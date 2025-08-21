package com.biryuchenko.room.repository.interfaces


import com.biryuchenko.room.entities.Document
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getAllItemsStream(): Flow<List<Document>>

    fun getItemStream(uid: Long): Flow<Document?>

    fun filterByDate(firstDate: Long, lastDate: Long): Flow<Document?>

    suspend fun insertDocument(document: Document)

    suspend fun deleteItem(document: Document)

}