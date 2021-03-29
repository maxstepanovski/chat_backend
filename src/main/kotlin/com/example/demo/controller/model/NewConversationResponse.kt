package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NewConversationResponse(
        @JsonProperty(value = "conversation_id")
        val conversationId: Long
)
