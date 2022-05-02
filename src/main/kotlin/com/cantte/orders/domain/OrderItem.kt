package com.cantte.orders.domain

import com.cantte.products.domain.Product
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,

    @ManyToOne(fetch = FetchType.LAZY) val product: Product,

    val quantity: Int
) {

    constructor(product: Product, quantity: Int) : this(0, product, quantity)

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var order: Order

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as OrderItem

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
