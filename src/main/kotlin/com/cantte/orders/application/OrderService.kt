package com.cantte.orders.application

import com.cantte.customers.domain.CustomerRepository
import com.cantte.orders.application.create.CreateOrderCommand
import com.cantte.orders.domain.Order
import com.cantte.orders.domain.OrderItem
import com.cantte.orders.domain.OrderRepository
import com.cantte.products.domain.ProductRepository
import java.util.*

class OrderService(
    private val repository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) {

    fun save(command: CreateOrderCommand): Optional<Order> {
        val customer = customerRepository.findById(command.customerId)

        if (customer.isEmpty) {
            return Optional.empty()
        }
        val deliveryAddress =
            customer.get().getAddresses().find { it.id == command.deliveryAddressId } ?: return Optional.empty()

        val products = productRepository.findAllById(command.orderItems.map { it.productCode })
        if (products.count() != command.orderItems.size) {
            return Optional.empty()
        }

        val items = products.map { product ->
            OrderItem(
                product, command.orderItems.find { it.productCode == product.code }!!.quantity
            )
        }

        val order = Order(customer.get(), deliveryAddress, items.toMutableSet(), command.createdAt, command.deliveredAt)

        return Optional.of(repository.save(order))
    }
}