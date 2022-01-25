package com.tbarauskas.features.driver.dto

data class DriverResponse(
    val name: String,
    val surname: String,
    val age: String,
    val driverLicense: String
)

data class DriverWithAddress(
    val name: String,
    val surname: String,
    val age: String,
    val driverLicense: String,
    val country: String? = null,
    val city: String? = null,
    val street: String? = null,
    val houseNumber: String? = null,
    val flatNumber: String? = null
)
