package com.cantte.products.domain

import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, String>