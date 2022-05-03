package com.cantte.orders.domain

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "payments")
data class Payment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,

    val type: PaymentType,
    val amount: Float,
) {

    constructor(type: PaymentType, amount: Float) : this(0, type, amount)

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var order: Order

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Payment

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
