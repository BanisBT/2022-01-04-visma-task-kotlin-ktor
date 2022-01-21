package com.tbarauskas.features.slack

import com.slack.api.Slack
import org.koin.dsl.module

fun slackServiceModule(slackConfig: SlackConfig) = module {
    single {
        Slack.getInstance()
    }
    single {
        SlackService(slackConfig, get())
    }
}
