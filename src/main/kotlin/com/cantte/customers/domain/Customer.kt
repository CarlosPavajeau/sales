package com.cantte.customers.domain

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "customers")
data class Customer(
    @Id val id: String,
    var name: String,
    var lastName: String,
    var email: String,

    @OneToMany(fetch = FetchType.LAZY) var addresses: MutableSet<Address> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY) var phoneNumbers: MutableSet<PhoneNumber> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Customer

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
