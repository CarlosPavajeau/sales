package com.cantte.orders.application.create

import java.util.*

data class CreateOrderCommand(
    val customerId: String,
    val deliveryAddressId: Long,
    val orderItems: List<CreateOrderItemCommand>,
    val payments: List<CreatePaymentCommand>,
    val createdAt: Date,
    val deliveredAt: Date?
)
