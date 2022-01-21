package com.tbarauskas.features.messageQueue

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tbarauskas.features.messageQueue.MessageQueueService
import com.tbarauskas.features.rabbitMq.RabbitMqConfig
import org.koin.dsl.module
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.RabbitMQConfiguration
import pl.jutupe.ktor_rabbitmq.RabbitMQInstance

fun messageQueueServiceModule(rabbitMqConfig: RabbitMqConfig) = module {
    single {
       val rabbitMQInstance = RabbitMQInstance(
            RabbitMQConfiguration.create()
            .apply {
                uri = "amqp://guest:guest@${rabbitMqConfig.host}:${rabbitMqConfig.amqpPort}"
                connectionName = rabbitMqConfig.connectionName

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
        rabbitMqConfig
    }
    single {
        jacksonObjectMapper()
    }
    single {
        MessageQueueService(get())
    }
}
