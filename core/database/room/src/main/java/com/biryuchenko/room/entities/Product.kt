package com.biryuchenko.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "products",
    foreignKeys = [ForeignKey(
        entity = Document::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("documentId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "priceForOne") val priceForOne: Int,
    @ColumnInfo(name = "documentId") val documentId: Long,
)
