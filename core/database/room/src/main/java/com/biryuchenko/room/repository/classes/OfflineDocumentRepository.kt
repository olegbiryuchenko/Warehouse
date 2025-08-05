package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.DocumentDao
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.repository.interfaces.DocumentRepository
import kotlinx.coroutines.flow.Flow

class OfflineDocumentRepository(val docDao: DocumentDao) : DocumentRepository {
    override fun getAllItemsStream(): Flow<List<Document>> {
        return docDao.getAll()
    }

    override fun getItemStream(uid: Long): Flow<Document?> {
        return docDao.findByName(uid)
    }

    override suspend fun insertCategory(category: Document) {
        return docDao.insertAll(category)
    }

    override suspend fun deleteItem(category: Document) {
        return deleteItem(category)
    }
}