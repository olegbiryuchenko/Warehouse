package com.biryuchenko.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class Folder (
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "folder_name") val folderName: String,
    @ColumnInfo(name = "date") val date: Long,
)