package com.cantte.sales.unit

import com.cantte.products.application.ProductService
import com.cantte.products.application.create.CreateProductCommand
import com.cantte.products.domain.Product
import com.cantte.products.domain.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class ProductServiceTest {

    private val repository: ProductRepository = Mockito.mock(ProductRepository::class.java)
    private val product = Product("321", "Teléfono", 150000f, 0.19f)

    private val service = ProductService(repository)

    @Test
    fun `test save product should save product`() {
        Mockito.`when`(repository.save(Mockito.any())).thenReturn(product)

        val response = service.save(CreateProductCommand("321", "Teléfono", 150000f, 0.19f))

        Mockito.verify(repository).save(product)
        assertEquals(product.code, response.code)
    }

    @Test
    fun `test search all product should be return all products`() {
        Mockito.`when`(repository.findAll()).thenReturn(listOf(product))

        val response = service.searchAll()

        Mockito.verify(repository).findAll()
        assertEquals(product.code, response[0].code)
    }
}