package com.tbarauskas.features.driver

import com.tbarauskas.features.driver.dto.DriverView
import com.tbarauskas.features.driver.dto.DriverWithAddress

data class Driver(
    val driverId: Long,
    val name: String,
    val surname: String,
    val age: Int?,
    val driverLicense: String,
    val addressId: Long?,
    val country: String?,
    val city: String?,
    val street: String?,
    val houseNumber: Int?,
    val flatNumber: Int?
)

fun Driver.toDriverView() = DriverView(
    name = name,
    surname = surname,
    age = if (age == null) "Driver age not set" else "Driver age is - $age",
    driverLicense = driverLicense
)

fun Driver.toDriverWithAddressView() = DriverWithAddress(
    name = name,
    surname = surname,
    age = if (age == null) "Driver age not set" else "Driver age is - $age",
    driverLicense = driverLicense,
    country = if (country == null) "No country set" else "$country",
    city = if (city == null) "No city set" else "$city",
    street = if (street == null) "No street set" else "$street",
    houseNumber = if (houseNumber == null) "No house number set" else "$houseNumber",
    flatNumber = if (flatNumber == null) "No flat number set" else "$flatNumber",
)
