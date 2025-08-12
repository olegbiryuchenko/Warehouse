package com.biryuchenko.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithDocument(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "documentId",
        entityColumn = "uid"
    )
    val document: Document
)
