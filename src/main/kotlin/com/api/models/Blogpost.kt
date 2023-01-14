package com.api.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoCollation
import io.micronaut.serde.annotation.Serdeable
import org.bson.types.ObjectId

@MongoCollation
@Serdeable.Deserializable
@Serdeable.Serializable
data class Blogpost(
    @field:Id
    @field:GeneratedValue
    val id: String? = null,

    val name: String,

    val text: String,

    val category: String,

    val productIds: MutableList<ObjectId?> = mutableListOf(),

    val products: MutableList<Product?> = mutableListOf()

)
