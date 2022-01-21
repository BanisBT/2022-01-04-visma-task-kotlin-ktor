package com.tbarauskas.plugins

import com.tbarauskas.features.driver.registerDriverRoutes
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    registerDriverRoutes()
}
