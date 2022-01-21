package com.tbarauskas.features.driver

import com.tbarauskas.features.driver.dto.DriverView

class DriverService(
    private val driverDAO: DriverDAO
) {

    fun getAllDrivers(): List<DriverView> {
        return driverDAO.getAllDrivers().map { it.toDriverView() }
    }
}
