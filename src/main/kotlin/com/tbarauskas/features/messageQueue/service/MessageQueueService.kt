package com.tbarauskas.features.messageQueue.service

import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.publish

class MessageQueueService(
    private val rabbitMq: RabbitMQ
) {
    fun <T>publish(exchange: String, routingKey: String, body: T) {
        rabbitMq.publish(exchange, routingKey, null, body)
    }
}
