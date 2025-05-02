package com.test.murni.core

data class ErrorResponse(
    val success: Boolean? = null,
    val code: Int? = null,
    val message: String? = null,
    val errors: Error? = null
)
