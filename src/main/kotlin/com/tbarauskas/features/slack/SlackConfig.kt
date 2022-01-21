package com.tbarauskas.features.slack

import com.typesafe.config.Config
import io.github.config4k.extract

data class SlackConfig(
    val configMap: Map<SlackAppName, SlackConfigNvm>
) {
    companion object {
        fun fromConfig(config: Config): SlackConfig {
            val mapKeyNotEnum: Map<String, SlackConfigNvm> = config.extract("slackMapNvm")
            val slackConfigMap: Map<SlackAppName, SlackConfigNvm> =
                mapKeyNotEnum.map { SlackAppName.valueOf(it.key) to it.value }.toMap()

            return SlackConfig(slackConfigMap)
        }
    }
}

data class SlackConfigNvm(
    val oauthToken: String,
    val slackChannelId: String
)
