package com.tbarauskas.plugins

import com.tbarauskas.database.PostgresqlDataBaseConfig
import com.tbarauskas.database.postgresqlDbModule
import com.tbarauskas.features.messageQueue.messageQueueServiceModule
import com.tbarauskas.features.rabbitMq.RabbitMqConfig
import com.tbarauskas.features.slack.SlackConfig
import com.tbarauskas.features.slack.slackServiceModule
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin

fun Application.configureDependencyInjection(vararg moduleOverrides: Module) {
//    Kaip galima issitraukti objeektus is application aplinkos
//    val rabbitMq = this.attributes[RabbitMQ.RabbitMQKey]
    val config = ConfigFactory.load()

    val slackConfig: SlackConfig = SlackConfig.fromConfig(config)
    val rabbitMQConfig: RabbitMqConfig = RabbitMqConfig.fromConfig(config)
    val postgresqlConfig: PostgresqlDataBaseConfig = PostgresqlDataBaseConfig.fromConfig(config)

    install(Koin) {
        modules(
            messageQueueServiceModule(rabbitMQConfig), slackServiceModule(slackConfig),
            postgresqlDbModule(postgresqlConfig),
            *moduleOverrides
        )
    }
}
