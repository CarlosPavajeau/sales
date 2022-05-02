package com.cantte.orders.application

import com.cantte.customers.application.AddressResponse
import com.cantte.customers.application.CustomerResponse
import com.cantte.products.application.ProductResponse
import java.util.*

data class OrderItemResponse(
    val id: Long,
    val product: ProductResponse,
    val quantity: Int
)

data class OrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val deliveryAddress: AddressResponse,
    val items: List<OrderItemResponse>,
    val createdAt: Date,
    val deliveredAt: Date?
)
