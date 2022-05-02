package com.cantte.products.application

data class ProductResponse(
    val code: String,
    val name: String,
    val price: Float,
    val tax: Float
)
