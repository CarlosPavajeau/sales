package com.cantte.orders.application.create

data class CreateOrderItemCommand(
    val productCode: String,
    val quantity: Int
)
