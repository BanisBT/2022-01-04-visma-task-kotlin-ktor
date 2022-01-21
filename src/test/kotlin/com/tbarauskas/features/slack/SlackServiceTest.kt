package com.tbarauskas.features.slack

import com.tbarauskas.configure
import com.tbarauskas.features.slack.SlackAppName.BANIS
import com.tbarauskas.features.slack.SlackAppName.BANIS_TEST
import io.ktor.server.testing.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

internal class SlackServiceTest : KoinTest {

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test get slack config map from application conf`() {
        withTestApplication({ configure() }) {
            val slackService: SlackService by inject()

            assertEquals("C02S8K8FZR6", slackService.getSlackConfig().configMap[BANIS]?.slackChannelId)
            assertEquals("C02RWMDPQAD", slackService.getSlackConfig().configMap[BANIS_TEST]?.slackChannelId)
        }
    }

    @Test
    fun `test send plain text message to slack`() {
        withTestApplication({ configure() }) {
            val slackService: SlackService by inject()

            slackService.sendPlainTextMessageToSlack("testing message", BANIS)
        }
    }
}
