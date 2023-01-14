package com.api.dto.requests

import com.api.models.Categories
import com.api.models.Product
import org.bson.types.ObjectId

data class BlogpostRequest(
    val name: String,
    val text: String,
    val category: String,
    val products: MutableList<ObjectId?> = mutableListOf()
)

