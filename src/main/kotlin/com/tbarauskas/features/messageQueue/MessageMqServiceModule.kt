package com.tbarauskas.features.messageQueue

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tbarauskas.features.messageQueue.MessageQueueService
import org.koin.dsl.module
import pl.jutupe.ktor_rabbitmq.RabbitMQ

fun messageQueueServiceModule(rabbitMq: RabbitMQ) = module {
    single {
        rabbitMq
    }
    single {
        jacksonObjectMapper()
    }
    single {
        MessageQueueService(rabbitMq)
    }
}
