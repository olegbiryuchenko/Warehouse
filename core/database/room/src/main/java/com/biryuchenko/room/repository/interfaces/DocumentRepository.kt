package com.biryuchenko.room.repository.interfaces


import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.FolderWithDocument
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getAllItemsStream(): Flow<List<Document>>

    fun get(folderId: Long): Flow<List<FolderWithDocument>>
    fun getItemStream(uid: Long): Flow<Document?>

    fun filterByDate(firstDate: Long, lastDate: Long): Flow<List<Document>>

    fun filterByOneDate(firstDate: Long): Flow<List<Document>>
    suspend fun insertDocument(document: Document)

    suspend fun deleteItem(document: Document)



}