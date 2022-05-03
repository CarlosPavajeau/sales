package com.cantte.orders.application.create

import java.time.Instant
import java.util.*

data class CreateOrderCommand(
    val customerId: String,
    val deliveryAddressId: Long,
    val items: List<CreateOrderItemCommand>,
    val payments: List<CreatePaymentCommand>,
    val createdAt: Date = Date.from(Instant.now()),
    val deliveredAt: Date? = Date.from(Instant.now())
)
