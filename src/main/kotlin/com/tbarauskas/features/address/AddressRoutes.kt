package com.tbarauskas.features.address

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.addressRouting() {
    Database.connect("jdbc:pgsql://localhost:5432/fleetMaster2022",
        user = "fleetMaster2022", password = "fleetmaster2022")
    transaction {
        SchemaUtils.create(Addresses)
    }
    val persons = Persons.selectAll()

    route("/address") {

        get("/all") {
            call.respond(persons)
        }
    }
}

fun Application.registerAddressRouting() {
    routing {
        addressRouting()
    }
}
