package com.tbarauskas.features.rabbitMq.configuration

import com.typesafe.config.Config

data class RabbitMqConfig(
    val uri: String,
    val connectionName: String
) {
    companion object {
        fun fromConfig(config: Config): RabbitMqConfig {
            val rabbitMqBranch = config.getConfig("rabbitMq")
            return RabbitMqConfig(
                uri = rabbitMqBranch.getString("uri"),
                connectionName = rabbitMqBranch.getString("connectionName")
            )
        }
    }
}


