package com.cantte.products.application

import com.cantte.products.application.create.CreateProductCommand
import com.cantte.products.domain.Product
import com.cantte.products.domain.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val repository: ProductRepository) {

    fun save(command: CreateProductCommand): ProductResponse {
        val product = Product(command.code, command.name, command.price, command.tax)

        return repository.save(product).toResponse()
    }

    fun searchAll(): List<ProductResponse> {
        val products = repository.findAll()

        return products.map { it.toResponse() }
    }

    private fun Product.toResponse(): ProductResponse {
        return ProductResponse(this.code, this.name, this.price, this.tax)
    }
}