package com.api.controller

import com.api.constants.BlogpostConstants
import com.api.dto.requests.BlogpostRequest
import com.api.dto.requests.ProductRequest
import com.api.dto.responses.BlogpostResponse
import com.api.dto.responses.ProductResponse
import com.api.models.Blogpost
import com.api.service.BlogpostService
import com.api.service.ProductService
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import org.bson.BsonValue

@Controller("/blogpost")
class BlogpostController(
    private val blogpostService: BlogpostService,
    private val productService: ProductService
) {

    @Get("/{id}")
    @Schema(description = "Get a blogpost by id")
    fun getProduct(id: String): Blogpost {
        return blogpostService.findById(id);
    }

    @Post("/")
    fun create(
        @Schema(description = "Blogpost to create", example = BlogpostConstants.CREATE_REQUEST_BODY)
        blogpostRequest: BlogpostRequest
    ): BlogpostResponse {
        if (blogpostRequest.products.isNotEmpty()) {
            blogpostRequest.products.forEach { product ->
                try {
                    productService.findById(product?.toHexString()!!)
                } catch (e: Exception) {
                    throw Exception("Product with id ${product?.toHexString()} does not exist")
                }
            }
        }

        return BlogpostResponse(blogpostService.create(blogpostRequest), "Blogpost created successfully")
    }

    @Get("/")
    fun findAll(
        @QueryValue("limit", defaultValue = "20")
        @Schema(defaultValue = "20")
        limit: Int = 20,
        @QueryValue("offset", defaultValue = "0")
        @Schema(defaultValue = "0")
        offset: Int = 0
    ): List<String?> {
        return blogpostService.findAll(limit, offset)
    }

    @Get("/filter")
    fun filterAll(
        @Schema(description = "Field to search by", example="name")
        field: String,
        @Schema(description = "Search pattern", example="Blogpost name")
        value: String,
        @QueryValue("limit", defaultValue = "20")
        @Schema(defaultValue = "20")
        limit: Int = 20,
        @QueryValue("offset", defaultValue = "0")
        @Schema(defaultValue = "0")
        offset: Int = 0
    ): List<Blogpost?> {
        return blogpostService.filterAll(field, value, limit, offset)
    }

    @Patch("/{id}")
    fun update(
        id: String,
        @Schema(description = "Product to update", example = BlogpostConstants.CREATE_REQUEST_BODY)
        blogpostRequest: BlogpostRequest
    ): Blogpost {
        return blogpostService.update(id, blogpostRequest)
    }

    @Delete("/{id}")
    fun delete(id: String): BlogpostResponse {
        return BlogpostResponse(id , "Blogpost deleted successfully, delete count: " + blogpostService.delete(id))
    }
}