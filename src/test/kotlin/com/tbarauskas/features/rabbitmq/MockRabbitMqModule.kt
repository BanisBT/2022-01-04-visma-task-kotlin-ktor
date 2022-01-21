package com.tbarauskas.features.rabbitmq

import com.tbarauskas.features.rabbitMq.RabbitMqConfig
import org.koin.dsl.module

val mockRabbitMqModule = module {
    single {
        RabbitMqConfig(
            host = "localhostTest",
            amqpPort = "portTest",
            connectionName = "connectionTest"
        )
    }
}
