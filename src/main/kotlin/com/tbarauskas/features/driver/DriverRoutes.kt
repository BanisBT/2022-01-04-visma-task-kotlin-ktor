package com.tbarauskas.features.driver

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.driverRouting() {
    val driverService by inject<DriverService>()

    route("/driver") {

        get("/all") {
            call.respond(driverService.getAllDrivers())
        }

        get("/allWithAddress") {
            call.respond(driverService.getAllDriversWithAddress())
        }
    }
}

fun Application.registerDriverRoutes() {
    routing {
        driverRouting()
    }
}
