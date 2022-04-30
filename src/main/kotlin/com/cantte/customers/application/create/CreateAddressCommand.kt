package com.cantte.customers.application.create

data class CreateAddressCommand(
    val city: String,
    val state: String,
    val street: String,
    val zip: String? = null,
)