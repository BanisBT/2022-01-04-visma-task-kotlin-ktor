package com.tbarauskas.features.driver

import org.jdbi.v3.sqlobject.statement.SqlQuery

interface DriverDAO {

    @SqlQuery("select * from driver")
    fun getAllDrivers(): List<Driver>

    @SqlQuery("""SELECT d.driver_id, d.name, d.surname, d.driver_license, a.country, a.city, a.street, a.house_number,
        a.flat_number FROM driver d INNER JOIN address a ON d.address_id = a.address_id
    """)
    fun getAllDriversWithAddress(): List<Driver>
}
