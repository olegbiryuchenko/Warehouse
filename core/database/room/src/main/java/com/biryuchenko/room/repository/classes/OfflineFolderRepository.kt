package com.biryuchenko.room.repository.classes

import com.biryuchenko.room.dao.FolderDao

import com.biryuchenko.room.entities.Folder

import com.biryuchenko.room.repository.interfaces.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFolderRepository @Inject constructor(
    private val folderDao: FolderDao
)  : FolderRepository {
    override fun getAllItemsStream(): Flow<List<Folder>> {
        return folderDao.getAll()
    }

    override suspend fun insertDocument(folder: Folder) {
        return folderDao.insertAll(folder)
    }

    override suspend fun deleteItem(folder: Folder) {
        folderDao.delete(folder)
    }

}