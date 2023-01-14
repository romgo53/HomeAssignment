package com.api.service

import com.api.dto.requests.BlogpostRequest
import com.api.models.Blogpost
import com.api.models.Categories
import com.api.utils.Validators
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton
import org.bson.BsonValue
import org.bson.Document
import org.bson.types.ObjectId

@Singleton
class BlogpostService(
    private val mongoClient: MongoClient
) {

    fun findById(id: String): Blogpost {
        val pipeline = listOf(
            Aggregates.match(Filters.eq("_id", ObjectId(id))),
            Aggregates.lookup("product", "productIds", "_id", "products")
        )
        val res = getCollection().aggregate(pipeline).first()
        return res
            ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Blogpost with id $id not found")
    }

    fun create(blogpostRequest: BlogpostRequest): String {
        if (!Validators().validateCategory(blogpostRequest.category)) {
            throw HttpStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid category, must be one of ${Categories.values().joinToString()}"
            )
        }
        val createResult = getCollection().insertOne(
            blogpostRequest.toBlogpostEntity()
        )
        return createResult.insertedId?.asObjectId()?.value?.toHexString()
            ?: throw HttpStatusException(HttpStatus.BAD_REQUEST, "Could not create the Blogpost")

    }

    fun findAll(limit: Int, offset: Int): List<String?> {
        val pipeline = listOf(
            Aggregates.lookup("product", "productIds", "_id", "products"),
            Aggregates.skip(offset),
            Aggregates.limit(limit)
        )
        return getCollection()
            .aggregate(pipeline)
            .map { it.id }
            .toList()
    }

    fun filterAll(field: String, value: String, limit: Int, offset: Int): List<Blogpost?> {
        val pipeline = listOf(
            Aggregates.match(Filters.regex(field, value, "i")),
            Aggregates.lookup("product", "productIds", "_id", "products"),
            Aggregates.skip(offset),
            Aggregates.limit(limit)
        )
        return getCollection()
            .aggregate(
                pipeline
            )
            .toList()
    }

    fun pullDeletedProduct(productId: String) {
        getCollection().updateMany(
            Filters.eq("productIds", ObjectId(productId)),
            Document("\$pull", Document("productIds", ObjectId(productId)))
        )
    }
    fun update(id: String, blogpostRequest: BlogpostRequest): Blogpost {

        val updateResult = getCollection().updateOne(
            Document("_id", ObjectId(id)),
            Document("\$set", blogpostRequest.toBlogpostEntity())
        )
        if (updateResult.modifiedCount == 0L) {
            throw HttpStatusException(HttpStatus.NOT_FOUND, "Blogpost with id $id not found")
        }
        return findById(id)
    }

    fun delete(id: String): Long {
        return getCollection().deleteOne(Document("_id", ObjectId(id))).deletedCount
    }


    private fun getCollection(): MongoCollection<Blogpost> =
        mongoClient
            .getDatabase("blog")
            .getCollection("blogpost", Blogpost::class.java)

    private fun BlogpostRequest.toBlogpostEntity(): Blogpost {
        return Blogpost(
            id = null,
            name = this.name,
            text = this.text,
            category = this.category,
            productIds = this.products,
            products = mutableListOf()

        )
    }
}

