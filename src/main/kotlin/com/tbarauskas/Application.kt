package com.tbarauskas

import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import com.tbarauskas.plugins.*
import io.ktor.application.*

fun main() {
    embeddedServer(Jetty, port = 8080, host = "0.0.0.0") {
        configure()
    }.start(wait = true)
}

fun Application.configure() {
    configureRouting()
    configureMonitoring()
    configureTemplating()
    configureSerialization()
    configureDependencyInjection()
    configureMessageConfigure()
}
