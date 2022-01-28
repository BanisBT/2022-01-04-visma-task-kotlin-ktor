package com.tbarauskas.features.driver

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.tbarauskas.database.redis.CacheName
import com.tbarauskas.database.redis.RedisCacheService
import com.tbarauskas.features.driver.dto.DriverView
import com.tbarauskas.features.driver.dto.DriverWithAddress

class DriverService(
    private val driverDAO: DriverDAO,
    private val cacheService: RedisCacheService,
    private val mapper: ObjectMapper,
    private val timeToLiveSeconds: Long = 10
) {

    fun getDriverById(driverId: Long): DriverView {
        val entryKeyForCache = getCacheName(CacheName.DRIVER, driverId)
        val driverFromCache = cacheService.getDataFromCache(entryKeyForCache)

        if (driverFromCache != null) {
            val mapperDriver: Driver = mapper.readValue(driverFromCache)
            return mapperDriver.toDriverView()
        }

        val driverFromDb = driverDAO.getDriverById(driverId)
        cacheService.setDataToCache(entryKeyForCache, mapper.writeValueAsString(driverFromDb), timeToLiveSeconds)
        return driverFromDb.toDriverView()
    }

    fun getAllDrivers(): List<DriverView> {
        return driverDAO.getAllDrivers().map { it.toDriverView() }
    }

    fun getAllDriversWithAddress(): List<DriverWithAddress> {
        return driverDAO.getAllDriversWithAddress().map { it.toDriverWithAddressView() }
    }

    private fun getCacheName(cacheName: CacheName, driverId: Long): String {
        return "${cacheName.name}-${driverId}"
    }
}
