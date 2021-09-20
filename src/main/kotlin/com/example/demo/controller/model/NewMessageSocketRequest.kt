package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NewMessageSocketRequest(
    @JsonProperty(value = "conversation_id")
    val conversationId: Long,

    @JsonProperty(value = "message")
    val message: String
) : BaseSocketRequest(SocketRequestType.NEW_MESSAGE)