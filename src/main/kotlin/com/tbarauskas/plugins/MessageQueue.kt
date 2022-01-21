package com.tbarauskas.plugins

import com.tbarauskas.features.slack.SlackTryCatchException
import com.tbarauskas.features.rabbitMq.MyObject
import com.tbarauskas.features.slack.SlackAppName
import com.tbarauskas.features.slack.SlackService
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import pl.jutupe.ktor_rabbitmq.*

fun Application.configureMessageConfigure() {
    val rabbitMQ by inject<RabbitMQInstance>()
    val slackService: SlackService by inject()

    install(RabbitMQ) {
        rabbitMQInstance = rabbitMQ
    }

//publish example
    routing {
        get("anyEndpoint") {
            call.publish(
                "exchange", "routingKey", null,
                MyObject(1, "Routing", "test name")
            )
        }
    }

//consume with autoack example
//    rabbitConsumer {
//        consume<MyObject>("queue") { body ->
//            println("Consumed message $body")
//        }
//    }

//consume work queue with manual ack example
    rabbitConsumer {
//        consume<MyObject>("work_queue") { body ->
//            println("Consumed task $body")
//
//            // We can omit 'this' part
//            this.ack(multiple = false)
//        }
        consume<String>("queueTest", autoAck = false) { message ->
            try {
                println("Consume here: $message")
                slackService.sendPlainTextMessageToSlack(message, SlackAppName.BANIS)
                this.ack(multiple = true)
            } catch (e: SlackTryCatchException) {
                this.nack(multiple = true, requeue = true)
            }
        }
    }
}
