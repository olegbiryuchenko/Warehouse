package com.biryuchenko.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "productsDb" ,
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("categoryId"),
        onDelete = ForeignKey.NO_ACTION
    )])
data class ProductDb(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "barcode") val barcode: Int,
    @ColumnInfo(name = "categoryId") val categoryId: Long,
)

