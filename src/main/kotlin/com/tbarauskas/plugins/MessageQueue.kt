package com.tbarauskas.plugins

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tbarauskas.rabbitMq.MyObject
import io.ktor.application.*
import io.ktor.routing.*
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import pl.jutupe.ktor_rabbitmq.rabbitConsumer

fun Application.configureMessageConfigure() {

    install(RabbitMQ) {
        uri = "amqp://guest:guest@localhost:5672"
        connectionName = "Connection name"

        //serialize and deserialize functions are required
        serialize { jacksonObjectMapper().writeValueAsBytes(it) }
        deserialize { bytes, type -> jacksonObjectMapper().readValue(bytes, type.javaObjectType) }

        //example initialization logic
        initialize {
            exchangeDeclare("exchange", "direct", true)
            queueDeclare("work_queue", true, false, false, emptyMap())
            queueBind(
                "work_queue",
                "exchange",
                "routingKey"
            )
        }
    }

//publish example
    routing {
        get("anyEndpoint") {
            call.publish("exchange", "routingKey", null,
                MyObject(1, "Routing" ,"test name"))
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
        consume<MyObject>("work_queue") { body ->
            println("Consumed task $body")

            // We can omit 'this' part
            this.ack(multiple = false)
        }
    }
}
