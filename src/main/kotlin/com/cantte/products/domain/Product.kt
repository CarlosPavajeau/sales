package com.cantte.products.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    @Id val code: String, var name: String, var price: Float, var tax: Float
)
