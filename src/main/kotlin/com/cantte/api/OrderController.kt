package com.cantte.api

import com.cantte.orders.application.OrderResponse
import com.cantte.orders.application.OrderService
import com.cantte.orders.application.create.CreateOrderCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(private val service: OrderService) {

    @PostMapping
    fun save(@RequestBody command: CreateOrderCommand): ResponseEntity<OrderResponse> {
        val order = service.save(command)

        if (order.isEmpty) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(order.get())
    }
}