package com.cantte.orders.application

import com.cantte.customers.application.AddressResponse
import com.cantte.customers.application.CustomerResponse
import com.cantte.orders.domain.PaymentType
import com.cantte.products.application.ProductResponse
import java.util.*

data class OrderItemResponse(
    val id: Long, val product: ProductResponse, val quantity: Int
)

data class PaymentResponse(
    val type: PaymentType, val amount: Float
)

data class OrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val deliveryAddress: AddressResponse,
    val items: List<OrderItemResponse>,
    val payments: List<PaymentResponse>,
    val createdAt: Date,
    val deliveredAt: Date?
)
