package com.biryuchenko.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithCategory(
    @Embedded val product: ProductDb,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "uid"
    )
    val categoryDetails: Category
)
