package com.cantte.sales.unit

import com.cantte.customers.application.CustomerService
import com.cantte.customers.application.create.CreateAddressCommand
import com.cantte.customers.application.create.CreateCustomerCommand
import com.cantte.customers.domain.Customer
import com.cantte.customers.domain.CustomerRepository
import com.cantte.customers.domain.PhoneNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

class CustomerServiceTest {

    private val repository: CustomerRepository = Mockito.mock(CustomerRepository::class.java)
    private val customer = Customer("321", "John", "Doe", "cantte098@gmail.com")

    private val service = CustomerService(repository)

    @Test
    fun `test save customer`() {
        Mockito.`when`(repository.save(Mockito.any())).thenReturn(customer)

        val response = service.save(CreateCustomerCommand("321", "John", "Doe", "cantte098@gmail.com"))

        assertEquals(customer.id, response.id)
    }

    @Test
    fun `test find customer should return a customer`() {
        Mockito.`when`(repository.findById("321")).thenReturn(Optional.of(customer))

        val response = service.find("321")
        val find = response.get()

        assertEquals(customer.id, find.id)
    }

    @Test
    fun `test find customer should return null`() {
        Mockito.`when`(repository.findById("123")).thenReturn(Optional.empty())

        val response = service.find("123")

        assertEquals(Optional.empty<Customer>(), response)
    }

    @Test
    fun `test add customer phone number`() {
        Mockito.`when`(repository.findById("321")).thenReturn(Optional.of(customer))

        service.addPhone("321", "1234567890")

        val response = service.find("321")

        assertTrue(response.get().phoneNumbers.isNotEmpty())
        assertTrue(response.get().phoneNumbers.contains(PhoneNumber("1234567890")))
    }

    @Test
    fun `test add customer phone number should return null`() {
        Mockito.`when`(repository.findById("123")).thenReturn(Optional.empty())

        val response = service.addPhone("123", "1234567890")

        assertEquals(Optional.empty<Customer>(), response)
    }

    @Test
    fun `test add customer address should return customer`() {
        Mockito.`when`(repository.findById("321")).thenReturn(Optional.of(customer))

        service.addAddress("321", CreateAddressCommand("Street", "City", "State", "12345"))

        val response = service.find("321")

        assertTrue(response.get().addresses.isNotEmpty())
    }
}