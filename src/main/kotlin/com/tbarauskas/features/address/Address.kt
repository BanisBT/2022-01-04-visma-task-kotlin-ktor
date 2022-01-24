package com.tbarauskas.features.address

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

data class Address(
    val addressId: Int,
    val country: String?,
    val city: String?,
    val houseAddress: String?
)

object Addresses: Table() {
    val addressId: Column<Int> = integer("address_id").autoIncrement()
    val country: Column<String?> = varchar("country", 30).nullable()
    val city: Column<String?> = varchar("city", 50).nullable()
    val houseAddress: Column<String?> = varchar("house_address", 45).nullable()

    override val primaryKey = PrimaryKey(addressId, name = "PK_address_ID")
}

data class Person(
    val personId: Int,
    val name: String,
    val surname: String,
    val address: Address
)

object Persons: Table() {
    val personId: Column<Int> = integer("person_id").autoIncrement()
    val name: Column<String> = varchar("name", 25)
    val surname: Column<String> = varchar("surname", 35)
    val addressId: Column<Int> = reference("address_id", Addresses.addressId).uniqueIndex()
//    val addressId: Column<Int> = integer("address_id").uniqueIndex().references(Addresses.addressId)
}
