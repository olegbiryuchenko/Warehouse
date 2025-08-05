package com.biryuchenko.dataserializer

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val percent:Int,
)
@Serializable
data class MyData(
    val data: List<Category>
)