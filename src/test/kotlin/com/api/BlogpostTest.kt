package com.api

import com.api.constants.BlogpostTestConstants
import com.api.dto.requests.BlogpostRequest
import com.api.service.BlogpostService
import io.micronaut.context.ApplicationContext
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

@MicronautTest
class BlogpostTest {

    // Test the Blogpost controller
    @Inject
    lateinit var blogpostService: BlogpostService
    var blogPostId: String = "";

    @BeforeEach
    fun setup() {
        blogpostService = ApplicationContext.run().getBean(BlogpostService::class.java)
    }

    @Test
    fun testPostNewBlogPost() {
        val result = blogpostService.create(BlogpostTestConstants.blogpostRequest)
        assertNotNull(result)
        this.blogPostId = result;
    }

    @Test
    fun testGetBlogpost() {
        val result = blogpostService.findById(this.blogPostId)
        assertEquals(result.text, BlogpostTestConstants.blogpostRequest.text)
    }
    @Test
    fun testUpdateBlogpost() {
        val result = blogpostService.update(this.blogPostId, BlogpostTestConstants.blogpostRequestUpdate)
        assertEquals(result.name, BlogpostTestConstants.blogpostRequestUpdate.name)
    }

    @Test
    fun testDeleteBlogpost() {
        val result = blogpostService.delete(this.blogPostId)
        // Returns the deleted count
        assert(result > 0)
    }

    @Test
    fun testGetNonExistentBlogpost() {
        assertThrows<HttpStatusException> {
            blogpostService.findById(BlogpostTestConstants.blogpostFakeId)
        }
    }
}
