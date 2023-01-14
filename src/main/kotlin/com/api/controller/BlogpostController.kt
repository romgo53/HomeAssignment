package com.api.controller

import com.api.dto.requests.BlogpostRequest
import com.api.dto.requests.ProductRequest
import com.api.dto.responses.BlogpostResponse
import com.api.dto.responses.ProductResponse
import com.api.models.Blogpost
import com.api.service.BlogpostService
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import org.bson.BsonValue

@Controller("/blogpost")
class BlogpostController(
    private val blogpostService: BlogpostService
) {

    @Get("/{id}")
    fun getProduct(id: String): Blogpost {
        return blogpostService.findById(id);
    }

    @Post("/")
    fun create(
        @Schema(description = "Blogpost to create",)
        blogpostRequest: BlogpostRequest
    ): BlogpostResponse {
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
    ): List<Blogpost?> {
        return blogpostService.filterAll(field, value, limit, offset)
    }

    @Patch("/{id}")
    fun update(
        id: String,
        @Schema(description = "Product to update")
        blogpostRequest: BlogpostRequest
    ): Blogpost {
        return blogpostService.update(id, blogpostRequest)
    }

    @Delete("/{id}")
    fun delete(id: String): BlogpostResponse {
        return BlogpostResponse(id , "Blogpost deleted successfully, delete count: " + blogpostService.delete(id))
    }
}