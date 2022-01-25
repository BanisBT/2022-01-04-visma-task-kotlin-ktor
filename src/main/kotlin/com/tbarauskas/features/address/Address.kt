package com.tbarauskas.features.address

data class Address(
    val addressId: Long,
    val country: String,
    val city: String,
    val street: String,
    val houseNumber: Int,
    val flatNumber: Int?
)
