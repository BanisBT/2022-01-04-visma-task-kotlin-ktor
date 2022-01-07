package com.tbarauskas.features.slack

import com.tbarauskas.configure
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

import com.tbarauskas.features.slack.SlackAppName.BANIS
import com.tbarauskas.features.slack.SlackAppName.BANIS_TEST

internal class SlackServiceTest: KoinTest {

    @Test
    fun `test get slack config map from application conf`() {
        withTestApplication({configure()}) {
            val slackService: SlackService by inject()

            assertEquals("xoxb-2861403370166-2871221076948-6Zds0zewz54ku8BxpCRXHUSU",
                slackService.slackConfig.configMap[BANIS]?.oauthToken)
            assertEquals("C02S8K8FZR6", slackService.slackConfig.configMap[BANIS]?.slackChannelId)
            assertEquals("C02RWMDPQAD", slackService.slackConfig.configMap[BANIS_TEST]?.slackChannelId)
            assertEquals("xoxb-2861403370166-2865731075077-z4v7qIgVzXPYH0E9LoXrKIK0",
            slackService.slackConfig.configMap[BANIS_TEST]?.oauthToken)
        }
    }
}
