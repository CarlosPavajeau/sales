package com.cantte.customers.domain

import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, String> {
}