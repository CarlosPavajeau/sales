package com.cantte.orders.application.create

import com.cantte.orders.domain.PaymentType

data class CreatePaymentCommand(
    val type: PaymentType,
    val amount: Float,
)