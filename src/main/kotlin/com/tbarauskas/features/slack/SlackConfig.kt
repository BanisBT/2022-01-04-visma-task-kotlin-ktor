package com.tbarauskas.features.slack

import com.typesafe.config.Config
import io.github.config4k.extract

data class SlackConfig(
    val configMap: Map<SlackAppName, SlackConfigNvm>
) {
    companion object {
        fun fromConfig(config: Config): SlackConfig {
            val mapKeyNotEnum: Map<String, SlackConfigNvm> = config.extract("slackMapNvm")
            val slackConfigMap: MutableMap<SlackAppName, SlackConfigNvm> = mutableMapOf()
            mapKeyNotEnum.forEach { slackConfigMap[SlackAppName.valueOf(it.key)] = it.value}

            return SlackConfig(slackConfigMap)
        }
    }
}

data class SlackConfigNvm(
    val oauthToken: String,
    val slackChannelId: String
)
