package com.cantte.customers.application

import com.cantte.customers.application.create.CreateAddressCommand
import com.cantte.customers.application.create.CreateCustomerCommand
import com.cantte.customers.domain.Address
import com.cantte.customers.domain.Customer
import com.cantte.customers.domain.CustomerRepository
import com.cantte.customers.domain.PhoneNumber
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomerService(private val repository: CustomerRepository) {

    @Transactional
    fun save(command: CreateCustomerCommand): CustomerResponse {
        val customer = Customer(command.id, command.name, command.lastName, command.email)

        return repository.save(customer).toResponse()
    }

    fun find(id: String): Optional<CustomerResponse> {
        val customer = repository.findById(id)

        return if (customer.isPresent) Optional.of(customer.get().toResponse())
        else Optional.empty()
    }

    @Transactional
    fun addPhone(id: String, phone: String): Optional<CustomerResponse> {
        val customer = repository.findById(id)

        if (customer.isPresent) {
            val phoneNumber = PhoneNumber(phone)

            customer.get().addPhoneNumber(phoneNumber)
            repository.save(customer.get())
        }

        return if (customer.isPresent) Optional.of(customer.get().toResponse())
        else Optional.empty()
    }

    @Transactional
    fun addAddress(id: String, command: CreateAddressCommand): Optional<CustomerResponse> {
        val customer = repository.findById(id)

        if (customer.isPresent) {
            val address = Address(command.city, command.state, command.street, command.zip)

            customer.get().addAddress(address)
            repository.save(customer.get())
        }

        return if (customer.isPresent) Optional.of(customer.get().toResponse())
        else Optional.empty()
    }

    fun Customer.toResponse(): CustomerResponse {
        return CustomerResponse(this.id,
            this.name,
            this.lastName,
            this.email,
            this.getAddresses().map { AddressResponse(it.id, it.city, it.state, it.street, it.zip) },
            this.getPhoneNumbers().map { PhoneNumberResponse(it.number) })
    }

}