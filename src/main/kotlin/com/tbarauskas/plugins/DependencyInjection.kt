package com.tbarauskas.plugins

import com.tbarauskas.features.messageQueue.messageQueueServiceModule
import com.tbarauskas.features.rabbitMq.RabbitMqConfig
import com.tbarauskas.features.slack.SlackConfig
import com.tbarauskas.features.slack.slackServiceModule
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import org.koin.ktor.ext.Koin
import pl.jutupe.ktor_rabbitmq.RabbitMQ

fun Application.configureDependencyInjection() {
//    Kaip galima issitraukti objeektus is application aplinkos
//    val rabbitMq = this.attributes[RabbitMQ.RabbitMQKey]
    val config = ConfigFactory.load()

    val slackConfig: SlackConfig = SlackConfig.fromConfig(config)
    val rabbitMQConfig: RabbitMqConfig = RabbitMqConfig.fromConfig(config)

    install(Koin) {
        modules(
            messageQueueServiceModule(rabbitMQConfig), slackServiceModule(slackConfig)
        )
    }
}
