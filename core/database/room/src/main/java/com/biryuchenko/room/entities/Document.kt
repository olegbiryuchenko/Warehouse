package com.biryuchenko.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "documents",
    foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = ["uid"],
            childColumns = ["folder_id"],
            onDelete = ForeignKey.CASCADE
        )
]
)
data class Document(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "document") val document: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "folder_id") val folderId: Long
)
