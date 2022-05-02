package com.cantte.orders.application.create

import java.util.*

data class CreateOrderCommand(
    val customerId: String,
    val deliveryAddressId: Long,
    val orderItems: List<CreateOrderItemCommand>,
    val createdAt: Date,
    val deliveredAt: Date?
)
