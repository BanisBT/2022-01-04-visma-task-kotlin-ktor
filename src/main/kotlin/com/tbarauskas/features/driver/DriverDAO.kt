package com.tbarauskas.features.driver

import org.jdbi.v3.sqlobject.statement.SqlQuery

interface DriverDAO {

    @SqlQuery("""select driver_id, name, surname, age, driver_license,
        address.address_id, address.country, address.city, address.house_address
     from driver full join address ON address.address_id=driver.address_id""")
    fun getAllDrivers(): List<Driver>

    @SqlQuery("""SELECT d.driver_id, d.name, d.surname, d.driver_license, a.country, a.city, a.street, a.house_number,
        a.flat_number FROM driver d INNER JOIN address a ON d.address_id = a.address_id
    """)
    fun getAllDriversWithAddress(): List<Driver>

    @SqlQuery("SELECT driver_id, name, surname, age, driver_license FROM driver WHERE driver_id = :driverId")
    fun getDriverById(driverId: Long): Driver
}
