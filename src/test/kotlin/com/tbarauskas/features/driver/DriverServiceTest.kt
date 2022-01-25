package com.tbarauskas.features.driver

import com.tbarauskas.configure
import io.ktor.server.testing.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

internal class DriverServiceTest : KoinTest {

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getDriverById() {
        withTestApplication({configure()}) {
            val driverService by inject<DriverService>()


        }
    }

    @Test
    fun getAllDrivers() {
    }

    @Test
    fun getAllDriversWithAddress() {
    }
}
