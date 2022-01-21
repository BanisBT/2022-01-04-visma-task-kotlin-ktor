package com.tbarauskas.features.rabbitMq

import com.typesafe.config.Config

data class RabbitMqConfig(
    val host: String,
    val amqpPort: String,
    val connectionName: String
) {
    companion object {
        fun fromConfig(config: Config): RabbitMqConfig {
            val rabbitMqBranch = config.getConfig("rabbitMq")
            return RabbitMqConfig(
                host = rabbitMqBranch.getString("host"),
                amqpPort = rabbitMqBranch.getString("amqpPort"),
                connectionName = rabbitMqBranch.getString("connectionName")
            )
        }
    }
}


