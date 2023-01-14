package com.api.constants

import com.api.dto.requests.BlogpostRequest
import com.api.models.Blogpost

object BlogpostTestConstants {
    val blogpostRequest = BlogpostRequest(
        "Test Blogpost",
        "This is a test blogpost",
        "Pants",
        mutableListOf()
    );
    val blogpostRequestUpdate = BlogpostRequest(
        "Test Blogpost1",
        "This is a test blogpost",
        "Pants",
        mutableListOf()
    );

    val blogpostFakeId = "555555555555555555555555";

}