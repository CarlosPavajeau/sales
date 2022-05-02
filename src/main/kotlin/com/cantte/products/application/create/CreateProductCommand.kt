package com.cantte.products.application.create

data class CreateProductCommand(
    val code: String,
    val name: String,
    val price: Float,
    val tax: Float
)
