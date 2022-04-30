package com.cantte.customers.application.create

data class CreateCustomerCommand(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
)
