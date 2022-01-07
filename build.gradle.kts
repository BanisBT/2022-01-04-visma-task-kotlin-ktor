val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val prometeus_version: String by project
val koin_version: String by project
val rabbitmq_version: String by project
val ktor_rabbitmq_feature: String by project
val config4k_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.tbarauskas"
version = "0.0.1"
application {
    mainClass.set("com.tbarauskas.ApplicationKt")
}

repositories {
    mavenCentral()
    maven ("https://jitpack.io")
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-metrics-micrometer:$ktor_version")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometeus_version")
    implementation("io.ktor:ktor-thymeleaf:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-jetty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

//    ConfigFactory extra methods (extract, ..)
    implementation("io.github.config4k:config4k:$config4k_version")

//    koin
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")

    //    rabbitMq
    implementation ("com.github.JUtupe:ktor-rabbitmq:$ktor_rabbitmq_feature")
    implementation( "com.rabbitmq:amqp-client:$rabbitmq_version")

//    test
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// for understanding junit5
tasks.test {
    useJUnitPlatform()
}
