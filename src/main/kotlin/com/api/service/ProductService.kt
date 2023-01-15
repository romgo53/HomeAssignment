package com.api.service

import com.api.dto.requests.ProductRequest
import com.api.models.Categories
import com.api.models.Product
import com.api.utils.Validators
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton
import org.bson.BsonValue
import org.bson.Document
import org.bson.types.ObjectId

@Singleton
class ProductService(
    private val mongoClient: MongoClient
) {

    fun findById(id: String): Product {
        val res = getCollection().find(Document("_id", ObjectId(id))).first()
        return res
            ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Product with id $id not found")
    }

    fun create(productRequest: ProductRequest): String {
        if (!Validators().validateCategory(productRequest.category)) {
            throw HttpStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid category, must be one of ${Categories.values().joinToString()}"
            )
        }
        val createResult = getCollection().insertOne(productRequest.toProductEntity())
        return createResult.insertedId?.asObjectId()?.value?.toHexString()
            ?: throw HttpStatusException(HttpStatus.BAD_REQUEST, "Could not create product")


    }

    fun findAll(limit: Int = 20, offset: Int = 0): List<String?> {
        return getCollection().find()
            .limit(limit)
            .skip(offset)
            .map { it.id }
            .toList()
    }

    fun filterAll(field: String, value: String, limit: Int, offset: Int): List<Product?> {
        return getCollection()
            .find(
                Filters.regex(field, value, "i")
            )
            .limit(limit)
            .skip(offset)
            .toList()
    }

    fun update(id: String, productRequest: ProductRequest): Product {
        val updateResult = getCollection().updateOne(
            Document("_id", ObjectId(id)),
            Document("\$set", productRequest.toProductEntity())
        )
        if (updateResult.modifiedCount == 0L) {
            throw HttpStatusException(HttpStatus.NOT_FOUND, "Product with id $id not found")
        }
        return findById(id)
    }

    fun delete(id: String): Long {
        return getCollection().deleteOne(Document("_id", ObjectId(id))).deletedCount
    }

    fun verifyProductsExists(product_ids: MutableList<ObjectId?> ) {
        product_ids.forEach()  { product_id ->
            try {
                findById(product_id?.toHexString()!!)
            } catch (e: Exception) {
                throw HttpStatusException(HttpStatus.BAD_REQUEST, "Product with id ${product_id?.toHexString()!!} does not exist")
            }
        }
    }
    private fun getCollection(): MongoCollection<Product> =
        mongoClient
            .getDatabase("blog")
            .getCollection("product", Product::class.java)

    private fun ProductRequest.toProductEntity(): Product {
        // Validate category, raises IllegalArgumentException if invalid
        return Product(
            id = null,
            name = this.name,
            price = this.price,
            category = this.category,
            brand = this.brand
        )
    }
}




