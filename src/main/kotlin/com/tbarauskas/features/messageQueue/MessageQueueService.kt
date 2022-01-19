package com.tbarauskas.features.messageQueue

import pl.jutupe.ktor_rabbitmq.RabbitMQInstance
import pl.jutupe.ktor_rabbitmq.publish

class MessageQueueService(
    private val rabbitMq: RabbitMQInstance
) {
    fun <T> publish(exchange: String, routingKey: String, body: T) {
        rabbitMq.publish(exchange, routingKey, null, body)
    }
}
