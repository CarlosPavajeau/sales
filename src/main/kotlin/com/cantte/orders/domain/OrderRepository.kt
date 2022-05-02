package com.cantte.orders.domain

import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, Long>