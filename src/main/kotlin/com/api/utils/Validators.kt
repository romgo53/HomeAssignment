package com.api.utils

import com.api.models.Categories

class Validators {
    fun validateCategory(category: String): Boolean {
        // Case-insensitive
        return Categories.values().any { it.name.uppercase() == category.uppercase() }
    }

}