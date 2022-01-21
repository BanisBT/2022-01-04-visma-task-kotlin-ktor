val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val prometeus_version: String by project
val koin_version: String by project
val rabbitmq_version: String by project
val ktor_rabbitmq_feature: String by project
val config4k_version: String by project
val jupiter_version: String by project
val mockk_version: String by project
val strikt_version: String by project
val slack_version: String by project
val jdbi3_version: String by project
val postgresql_version: String by project
val hikari_version: String by project


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

//    slack
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.slack.api:slack-api-client:$slack_version")

    // Add these dependencies if you want to use the Kotlin DSL for building rich messages
    implementation("com.slack.api:slack-api-model-kotlin-extension:$slack_version")
    implementation("com.slack.api:slack-api-client-kotlin-extension:$slack_version")

//    ConfigFactory extra methods (extract, ..)
    implementation("io.github.config4k:config4k:$config4k_version")

//    koin
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")

    //    rabbitMq
    implementation ("com.github.JUtupe:ktor-rabbitmq:$ktor_rabbitmq_feature")
    implementation( "com.rabbitmq:amqp-client:$rabbitmq_version")

//    database
    implementation("org.jdbi:jdbi3-core:$jdbi3_version")
    implementation("org.jdbi:jdbi3-kotlin:$jdbi3_version")
    implementation("org.jdbi:jdbi3-sqlobject:$jdbi3_version")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:$jdbi3_version")
    implementation("org.postgresql:postgresql:$postgresql_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")

//    test
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$jupiter_version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiter_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.strikt:strikt-jvm:$strikt_version")

}

// for project to understand junit5
tasks.test {
    useJUnitPlatform()
}
