package com.tbarauskas.features.slack

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.response.chat.ChatPostMessageResponse

class SlackService(
    private val slackConfig: SlackConfig,
    private val slack: Slack
) {
    fun sendPlainTextMessageToSlack(message: String, slackAppName: SlackAppName){
        val methodsClient: MethodsClient = slack.methods(slackConfig.configMap[slackAppName]?.oauthToken)

        val messageRequest: ChatPostMessageRequest = ChatPostMessageRequest.builder()
            .channel(slackConfig.configMap[slackAppName]?.slackChannelId)
            .text(message)
            .build()

        val messagePostResponse: ChatPostMessageResponse = methodsClient.chatPostMessage(messageRequest)

        if (!messagePostResponse.isOk) {
            throw SlackTryCatchException(messagePostResponse.error)
        }
    }

    fun getSlackConfig(): SlackConfig {
        return slackConfig
    }
}
