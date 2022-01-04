package com.tbarauskas.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import org.koin.ktor.ext.Koin
import pl.jutupe.ktor_rabbitmq.RabbitMQ

fun Application.configureDI() {
    val rabbitMq = this.attributes[RabbitMQ.RabbitMQKey]
    val config = ConfigFactory.load()

    install(Koin) {
        modules(

        )
    }
}
