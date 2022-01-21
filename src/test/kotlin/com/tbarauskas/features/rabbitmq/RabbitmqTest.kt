package com.tbarauskas.features.rabbitmq

import com.tbarauskas.configure
import com.tbarauskas.features.messageQueue.MessageQueueService
import com.tbarauskas.features.rabbitMq.RabbitMqConfig
import com.tbarauskas.features.slack.SlackAppName
import com.tbarauskas.features.slack.SlackService
import com.tbarauskas.features.slack.SlackTryCatchException
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import pl.jutupe.ktor_rabbitmq.RabbitMQInstance
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import kotlin.test.assertEquals

internal class RabbitmqTest: KoinTest {

    private val exchangeTest: String = "exchangeTest"
    private val keyTest: String = "routingKeyTest"
    private val queueTest: String = "queueTest"

    @Test
    fun `test rabbitMq config`() {
        withTestApplication({configure()}) {
            val rabbitMQ: RabbitMQInstance by inject()

            rabbitMQ.publish(exchangeTest, keyTest, null, "testing message")
            rabbitMQ.consume<String>(queueTest, autoAck = false) {message ->
                println("Consume here: $message")
                this.ack(multiple = true)
            }
        }
    }

    @Test
    fun `test if message go throw rabbitMq to slack`() {
        withTestApplication({configure()}) {
            val messageQueueService: MessageQueueService by inject()

            messageQueueService.publish(exchangeTest, keyTest, "testing message")
        }
    }

    @Test
    fun `test rabbitMq try catch block ack`() {
        withTestApplication({configure()}) {
            val rabbitMQ: RabbitMQInstance by inject()
            val slackService: SlackService by inject()

            rabbitMQ.publish(exchangeTest, keyTest, null, "test rabbitMq try catch block ack")
            rabbitMQ.consume<String>(queueTest, autoAck = false) { message ->
                try {
                    println("Consume here: $message")
                    slackService.sendPlainTextMessageToSlack(message, SlackAppName.BANIS)
                    this.ack(multiple = true)
                } catch (e : SlackTryCatchException) {
                    this.nack(multiple = true, requeue = true)
                }
            }
        }
    }

    @Test
    fun `test rabbitMq try catch block nack`() {
        withTestApplication({configure()}) {
            val rabbitMQ: RabbitMQInstance by inject()
            val slackService: SlackService by inject()

            rabbitMQ.publish(exchangeTest, keyTest, null, "test rabbitMq try catch block nack")
            rabbitMQ.consume<String>("queueTest", autoAck = false) { message ->
                try {
                    println("Consume here: $message")
                    slackService.sendPlainTextMessageToSlack(message, SlackAppName.BANIS_TEST)
                    this.ack(multiple = true)
                } catch (e : SlackTryCatchException) {
                    this.nack(multiple = true, requeue = true)
                }
            }
        }
    }

    @Test
    fun `test rabbitMq config data from application conf`() {
        withTestApplication({configure()}) {
            val rabbitMqConfig by inject<RabbitMqConfig>()

            assertEquals(rabbitMqConfig.host, "localhost")
            assertEquals(rabbitMqConfig.amqpPort, "5672")
            assertEquals(rabbitMqConfig.connectionName, "Connection name")
        }
    }
}
