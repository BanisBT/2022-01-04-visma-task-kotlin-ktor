package com.tbarauskas.plugins

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tbarauskas.features.rabbitMq.RabbitMqConfig
import com.tbarauskas.features.rabbitMq.MyObject
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.routing.*
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import pl.jutupe.ktor_rabbitmq.rabbitConsumer

fun Application.configureMessageConfigure() {

    install(RabbitMQ) {
        val config = ConfigFactory.load()
        val rabbitMqConfig = RabbitMqConfig.fromConfig(config)
        val mapper = jacksonObjectMapper()

        uri = rabbitMqConfig.uri
        connectionName = rabbitMqConfig.connectionName

        //serialize and deserialize functions are required
        serialize { mapper.writeValueAsBytes(it) }
        deserialize { bytes, type -> mapper.readValue(bytes, type.javaObjectType) }

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
                MyObject(1, "Routing" ,"test name")
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
        consume<MyObject>("work_queue") { body ->
            println("Consumed task $body")

            // We can omit 'this' part
            this.ack(multiple = false)
        }
    }
}
