package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.DocumentDao
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.FolderWithDocument
import com.biryuchenko.room.repository.interfaces.DocumentRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineDocumentRepository @Inject constructor(
    private val docDao: DocumentDao
) : DocumentRepository {
    override fun getAllItemsStream(): Flow<List<Document>> {
        return docDao.getAll()
    }

    override fun get(folderId: Long): Flow<List<FolderWithDocument>> {
        return docDao.get(folderId)
    }

    override fun getItemStream(uid: Long): Flow<Document?> {
        return docDao.findByName(uid)
    }

    override fun filterByDate(firstDate: Long, lastDate: Long):  Flow<List<Document>> {
        return docDao.filterByDate(firstDate, lastDate)
    }

    override fun filterByOneDate(firstDate: Long): Flow<List<Document>> {
        return docDao.filterByOneDate(firstDate)
    }

    override suspend fun insertDocument(document: Document) {
        return docDao.insertAll(document)
    }

    override suspend fun deleteItem(document: Document) {
        return docDao.delete(document)
    }
}