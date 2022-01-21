package com.tbarauskas.features.driver

import com.tbarauskas.features.driver.dto.DriverView

data class Driver(
    val id: Long,
    val name: String,
    val surname: String,
    val age: Int?,
    val driverLicense: String
)

fun Driver.toDriverView() = DriverView(
    name = name,
    surname = surname,
    age = if (age == null) "Driver age not set" else "Driver age is - $age",
    driverLicense = driverLicense
)
