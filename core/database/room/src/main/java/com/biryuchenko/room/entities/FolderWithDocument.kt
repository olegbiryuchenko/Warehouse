package com.biryuchenko.room.entities

import androidx.room.Embedded
import androidx.room.Relation


data class FolderWithDocument(
    @Embedded val document: Document,
    @Relation(
        parentColumn = "folder_id",
        entityColumn = "uid"
    )
    val folder: Folder
)