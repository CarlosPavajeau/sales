package com.cantte.customers.application

import com.cantte.customers.application.create.CreateAddressCommand
import com.cantte.customers.application.create.CreateCustomerCommand
import com.cantte.customers.domain.Address
import com.cantte.customers.domain.Customer
import com.cantte.customers.domain.CustomerRepository
import com.cantte.customers.domain.PhoneNumber
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CustomerService(private val repository: CustomerRepository) {

    fun save(command: CreateCustomerCommand): Customer {
        val customer = Customer(command.id, command.name, command.lastName, command.email)

        return repository.save(customer)
    }

    fun find(id: String): Optional<Customer> {
        return repository.findById(id)
    }

    fun addPhone(id: String, phone: String): Optional<Customer> {
        val customer = repository.findById(id)

        if (customer.isPresent) {
            customer.get().phoneNumbers.add(PhoneNumber(phone))
        }

        return customer
    }

    fun addAddress(id: String, command: CreateAddressCommand): Optional<Customer> {
        val customer = repository.findById(id)

        if (customer.isPresent) {
            val address = Address(command.city, command.state, command.street, command.zip)

            customer.get().addresses.add(address)
        }

        return customer
    }

}