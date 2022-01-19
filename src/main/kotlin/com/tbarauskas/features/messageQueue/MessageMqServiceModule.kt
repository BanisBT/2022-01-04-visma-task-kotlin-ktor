package com.tbarauskas.features.messageQueue

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tbarauskas.features.messageQueue.MessageQueueService
import org.koin.dsl.module
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.RabbitMQConfiguration
import pl.jutupe.ktor_rabbitmq.RabbitMQInstance

fun messageQueueServiceModule() = module {
    single {
       val rabbitMQInstance = RabbitMQInstance(
            RabbitMQConfiguration.create()
            .apply {
                uri = "amqp://guest:guest@localhost:5672"
                connectionName = "Connection name"

                serialize { jacksonObjectMapper().writeValueAsBytes(it) }
                deserialize { bytes, type -> jacksonObjectMapper().readValue(bytes, type.javaObjectType) }

                initialize {
                    exchangeDeclare("exchangeTest", "direct", true)
                    queueDeclare("queueTest", true, false, false, emptyMap())
                    queueBind("queueTest", "exchangeTest", "keyTest")
                }
            })

        rabbitMQInstance
    }
    single {
        jacksonObjectMapper()
    }
    single {
        MessageQueueService(get<RabbitMQInstance>())
    }
}
