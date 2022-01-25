package com.tbarauskas.features.driver

import com.tbarauskas.features.driver.dto.DriverView
import com.tbarauskas.features.driver.dto.DriverWithAddress

class DriverService(
    private val driverDAO: DriverDAO
) {

    fun getAllDrivers(): List<DriverView> {
        return driverDAO.getAllDrivers().map { it.toDriverView() }
    }

    fun getAllDriversWithAddress(): List<DriverWithAddress> {
        return driverDAO.getAllDriversWithAddress().map { it.toDriverWithAddressView() }
    }
}
