package com.cantte.orders.application

import com.cantte.customers.application.CustomerService.Companion.toResponse
import com.cantte.customers.domain.CustomerRepository
import com.cantte.orders.application.create.CreateOrderCommand
import com.cantte.orders.domain.Order
import com.cantte.orders.domain.OrderItem
import com.cantte.orders.domain.OrderRepository
import com.cantte.orders.domain.Payment
import com.cantte.products.application.ProductService.Companion.toResponse
import com.cantte.products.domain.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val repository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) {

    fun save(command: CreateOrderCommand): Optional<OrderResponse> {
        val customer = customerRepository.findById(command.customerId)

        if (customer.isEmpty) {
            return Optional.empty()
        }
        val addresses = customer.get().getAddresses()
        val deliveryAddress = addresses.find { it.id == command.deliveryAddressId } ?: return Optional.empty()

        val products = productRepository.findAllById(command.items.map { it.productCode })
        if (products.count() != command.items.size) {
            return Optional.empty()
        }

        val items = products.map { product ->
            OrderItem(
                product, command.items.find { it.productCode == product.code }!!.quantity
            )
        }

        val payments = command.payments.map { Payment(it.type, it.amount) }

        val order = Order(
            customer.get(),
            deliveryAddress,
            items.toMutableSet(),
            payments.toMutableSet(),
            command.createdAt,
            command.deliveredAt
        )

        return Optional.of(repository.save(order).toResponse())
    }

    companion object {
        fun Order.toResponse(): OrderResponse {
            return OrderResponse(
                id,
                customer.toResponse(),
                deliverAddress.toResponse(),
                items.map { it.toResponse() },
                payments.map { it.toResponse() },
                createdAt,
                deliveredAt
            )
        }

        private fun OrderItem.toResponse(): OrderItemResponse {
            return OrderItemResponse(
                id, product.toResponse(), quantity
            )
        }

        private fun Payment.toResponse(): PaymentResponse {
            return PaymentResponse(
                type, amount
            )
        }
    }
}