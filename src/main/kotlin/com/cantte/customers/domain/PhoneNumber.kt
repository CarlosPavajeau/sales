package com.cantte.customers.domain

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "phone_numbers")
data class PhoneNumber(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long, val number: String,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id") val customer: Customer? = null
) {
    constructor(number: String) : this(0, number)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PhoneNumber

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
