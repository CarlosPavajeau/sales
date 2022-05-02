package com.cantte.customers.application

data class PhoneNumberResponse(val number: String)

data class AddressResponse(
    val id: Long,
    val city: String,
    val state: String,
    val street: String,
    val zip: String? = null,
)

data class CustomerResponse(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val addresses: List<AddressResponse>,
    val phoneNumbers: List<PhoneNumberResponse>
)
