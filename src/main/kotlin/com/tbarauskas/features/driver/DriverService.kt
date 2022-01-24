package com.tbarauskas.features.driver

import com.tbarauskas.features.driver.dto.DriverResponse

class DriverService(
    private val driverDAO: DriverDAO
) {

    fun getAllDrivers(): List<DriverResponse> {
        return driverDAO.getAllDrivers().map { it.toDriverView() }
    }
}
