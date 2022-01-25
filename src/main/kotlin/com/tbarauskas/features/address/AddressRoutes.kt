package com.tbarauskas.features.address

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.addressRouting() {

    route("/address") {
        get("/all") {
            call.respondText("addresses")
        }
    }
}

fun Application.registerAddressRouting() {
    routing {
        addressRouting()
    }
}
