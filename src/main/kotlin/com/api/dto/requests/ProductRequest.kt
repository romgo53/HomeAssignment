package com.api.dto.requests

import com.api.models.Categories

data class ProductRequest(
    val name: String,
    val price: String,
    val category: String,
    val brand: String
)
