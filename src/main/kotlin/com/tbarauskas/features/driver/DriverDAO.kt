package com.tbarauskas.features.driver

import org.jdbi.v3.sqlobject.statement.SqlQuery

interface DriverDAO {

    @SqlQuery("select * from driver")
    fun getAllDrivers(): List<Driver>
}
