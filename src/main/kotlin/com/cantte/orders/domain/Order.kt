package com.cantte.orders.domain

import com.cantte.customers.domain.Address
import com.cantte.customers.domain.Customer
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,

    @ManyToOne(fetch = FetchType.LAZY) val customer: Customer,

    @ManyToOne(fetch = FetchType.LAZY) val deliverAddress: Address,

    @OneToMany(
        mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true
    ) val items: MutableSet<OrderItem>,

    @OneToMany(
        mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true
    ) val payments: MutableSet<Payment>,

    val createdAt: Date, val deliveredAt: Date?
) {

    constructor(
        customer: Customer,
        deliverAddress: Address,
        items: MutableSet<OrderItem>,
        payments: MutableSet<Payment>,
        createdAt: Date,
        deliveredAt: Date?
    ) : this(
        0, customer, deliverAddress, items, payments, createdAt, deliveredAt
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Order

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
