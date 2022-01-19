package com.tbarauskas.features.slack

import com.tbarauskas.configure
import com.tbarauskas.features.messageQueue.MessageQueueService
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

import com.tbarauskas.features.slack.SlackAppName.BANIS
import com.tbarauskas.features.slack.SlackAppName.BANIS_TEST
import org.junit.jupiter.api.AfterEach
import org.koin.core.context.stopKoin
import pl.jutupe.ktor_rabbitmq.RabbitMQInstance
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish

internal class SlackServiceTest: KoinTest {

    private val exchangeTest: String = "exchangeTest"
    private val keyTest: String = "routingKeyTest"
    private val queueTest: String = "queueTest"

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test get slack config map from application conf`() {
        withTestApplication({configure()}) {
            val slackService: SlackService by inject()

            assertEquals("C02S8K8FZR6", slackService.getSlackConfig().configMap[BANIS]?.slackChannelId)
            assertEquals("C02RWMDPQAD", slackService.getSlackConfig().configMap[BANIS_TEST]?.slackChannelId)
        }
    }

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
    fun `test send plain text message to slack`() {
        withTestApplication({configure()}) {
            val slackService: SlackService by inject()

           slackService.sendPlainTextMessageToSlack("testing message", BANIS)
        }
    }

//    @Test
//    fun `test if message go throw rabbitMq to slack`() {
//        withTestApplication({configure()}) {
//            val rabbitMQ: RabbitMQ by inject()
//            val slackService: SlackService by inject()
//
//            rabbitMQ.publish(exchangeTest, routingKey, null, "message all the way")
//            rabbitMQ.consume<String>(queueTest, autoAck = false) {message ->
//                println("Consume here: $message")
//                slackService.sendPlainTextMessageToSlack(message, BANIS)
//                this.ack(multiple = false)
//            }
//        }
//    }

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
                    slackService.sendPlainTextMessageToSlack(message, BANIS)
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
                    slackService.sendPlainTextMessageToSlack(message, BANIS_TEST)
                    this.ack(multiple = true)
                } catch (e : SlackTryCatchException) {
                    this.nack(multiple = true, requeue = true)
                }
            }
        }
    }
}
