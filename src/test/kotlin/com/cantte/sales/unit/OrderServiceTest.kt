package com.cantte.sales.unit

import com.cantte.customers.domain.Address
import com.cantte.customers.domain.Customer
import com.cantte.customers.domain.CustomerRepository
import com.cantte.orders.application.OrderService
import com.cantte.orders.application.create.CreateOrderCommand
import com.cantte.orders.application.create.CreateOrderItemCommand
import com.cantte.orders.domain.OrderRepository
import com.cantte.products.domain.Product
import com.cantte.products.domain.ProductRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.Instant
import java.util.*

class OrderServiceTest {

    private val repository: OrderRepository = mock(OrderRepository::class.java)
    private val customerRepository: CustomerRepository = mock(CustomerRepository::class.java)
    private val productRepository: ProductRepository = mock(ProductRepository::class.java)

    private val customer = Customer(
        "321",
        "John",
        "Doe",
        "cantte098@gmail.com",
        mutableSetOf(Address(1, "Street", "City", "State", "12345"))
    )

    private val products = listOf(
        Product("123", "Product 1", 10f, 0.0f),
    )

    private val orderService = OrderService(repository, customerRepository, productRepository)

    @Test
    fun `test save order should create order`() {
        Mockito.`when`(customerRepository.findById("321")).thenReturn(Optional.of(customer))
        Mockito.`when`(productRepository.findAllById(Mockito.any())).thenReturn(products)
        Mockito.`when`(repository.save(Mockito.any())).then { it.getArgument(0) }

        val command = CreateOrderCommand(
            "321",
            1,
            listOf(CreateOrderItemCommand("123", 1)),
            Date.from(Instant.now()),
            Date.from(Instant.now())
        )

        val order = orderService.save(command)

        assertTrue(order.isPresent)
    }
}