package com.biryuchenko.room.repository.interfaces


import com.biryuchenko.room.entities.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    fun getAllItemsStream(): Flow<List<Folder>>

    suspend fun insertDocument(folder: Folder)

    suspend fun deleteItem(folder: Folder)
}