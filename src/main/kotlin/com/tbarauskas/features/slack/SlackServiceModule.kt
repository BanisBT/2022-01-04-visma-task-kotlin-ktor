package com.tbarauskas.features.slack

import org.koin.dsl.module

fun slackServiceModule(slackConfig: SlackConfig) = module {
    single {
        SlackService(slackConfig)
    }
}
