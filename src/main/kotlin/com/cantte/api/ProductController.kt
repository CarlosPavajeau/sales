package com.cantte.api

import com.cantte.products.application.ProductResponse
import com.cantte.products.application.ProductService
import com.cantte.products.application.create.CreateProductCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val service: ProductService) {

    @PostMapping
    fun save(@RequestBody command: CreateProductCommand): HttpStatus {
        service.save(command)
        return HttpStatus.CREATED
    }

    @GetMapping
    fun searchAll(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(service.searchAll())
    }
}