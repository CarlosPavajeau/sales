package com.cantte.customers.domain

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "customers")
data class Customer(
    @Id val id: String, var name: String, var lastName: String, var email: String,

    @OneToMany(
        mappedBy = "customer", cascade = [CascadeType.ALL], orphanRemoval = true
    ) private val addresses: MutableSet<Address> = mutableSetOf(),

    @OneToMany(
        mappedBy = "customer", cascade = [CascadeType.ALL], orphanRemoval = true
    ) private val phoneNumbers: MutableSet<PhoneNumber> = mutableSetOf()
) {

    fun addAddress(address: Address) {
        addresses.add(address)
        address.customer = this
    }

    fun getAddresses() = addresses.toList()

    fun addPhoneNumber(phoneNumber: PhoneNumber) {
        phoneNumbers.add(phoneNumber)
        phoneNumber.customer = this
    }

    fun getPhoneNumbers() = phoneNumbers.toList()

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
