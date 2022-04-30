package com.cantte.api

import com.cantte.customers.application.CustomerService
import com.cantte.customers.application.create.CreateAddressCommand
import com.cantte.customers.application.create.CreateCustomerCommand
import com.cantte.customers.domain.Customer
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val service: CustomerService) {

    private val log = LoggerFactory.getLogger(CustomerController::class.java)

    @PostMapping
    fun save(@RequestBody command: CreateCustomerCommand): HttpStatus {
        service.save(command)
        return HttpStatus.CREATED
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): ResponseEntity<Customer> {
        val customer = service.find(id)

        if (customer.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(customer.get())
    }

    @PostMapping("/{id}/phone_numbers")
    fun addPhoneNumber(@PathVariable id: String, @RequestBody phoneNumber: String): ResponseEntity<Customer> {

        log.info("Received phone={} with id={}", phoneNumber, id)

        val customer = service.addPhone(id, phoneNumber)

        if (customer.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(customer.get())
    }

    @PostMapping("/{id}/addresses")
    fun addAddress(@PathVariable id: String, @RequestBody address: CreateAddressCommand): ResponseEntity<Customer> {
        val customer = service.addAddress(id, address)

        if (customer.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(customer.get())
    }
}