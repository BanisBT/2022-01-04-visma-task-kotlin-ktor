package com.tbarauskas.features.driver

import org.jdbi.v3.sqlobject.statement.SqlQuery

interface DriverDAO {

    @SqlQuery("""select driver_id, name, surname, age, driver_license,
        address.address_id, address.country, address.city, address.house_address
     from driver full join address ON address.address_id=driver.address_id""")
    fun getAllDrivers(): List<Driver>
}
