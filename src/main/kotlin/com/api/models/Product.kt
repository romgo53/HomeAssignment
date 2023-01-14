package com.api.models

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.annotation.Serdeable.Deserializable
import org.bson.types.ObjectId

@MappedEntity
@Serdeable.Serializable
@Deserializable
data class Product(
    @field:Id
    @field:GeneratedValue
    val id: String? = null,

    val brand: String,

    val name: String,

    val price: String,

    val category: String
)
