package com.api.controller

import com.api.dto.requests.ProductRequest
import com.api.dto.responses.ProductResponse
import com.api.models.Product
import com.api.service.BlogpostService
import com.api.service.ProductService
import io.micronaut.data.annotation.Query
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import org.bson.BsonValue
import org.bson.types.ObjectId
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Controller("/product")
class ProductController(
    private val productService: ProductService,
    private val blogpostService: BlogpostService
) {

    @Get("/{id}")
    fun getProduct(id: String): Product {
        return productService.findById(id);
    }

    @Post("/")
    fun create(
        @Schema(description = "Product to create")
        productRequest: ProductRequest
    ): ProductResponse {
        return ProductResponse(productService.create(productRequest), "Product created successfully")
    }

    @Get("/")
    fun findAll(
        @QueryValue("limit", defaultValue = "20")
        @Schema(defaultValue = "20", required = false)
        limit: Int = 20,
        @QueryValue("offset", defaultValue = "0")
        @Schema(defaultValue = "0", required = false)
        offset: Int = 0
    ): List<String?> {
        return productService.findAll(limit, offset)
    }

    @Get("/filter")
    fun filterAll(
        @Schema(description = "Field to search by", example="brand")
        field: String,
        @Schema(description = "Search pattern", example="Under Armour")
        value: String,
        @QueryValue("limit", defaultValue = "20")
        @Schema(defaultValue = "20")
        limit: Int = 20,
        @QueryValue("offset", defaultValue = "0")
        @Schema(defaultValue = "0")
        offset: Int = 0
    ): List<Product?> {
        return productService.filterAll(field, value, limit, offset)
    }

    @Patch("/{id}")
    fun update(
        id: String,
        @Schema(description = "Product to update")
        productRequest: ProductRequest
    ): Product {
        return productService.update(id, productRequest)
    }

    @Delete("/{id}")
    fun delete(id: String): ProductResponse {
        blogpostService.pullDeletedProduct(id);
        return ProductResponse(id, "Product deleted successfully, delete count: " + productService.delete(id))
    }
}