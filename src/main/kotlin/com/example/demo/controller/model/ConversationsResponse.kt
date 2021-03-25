package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ConversationsResponse(
        @JsonProperty(value = "conversations")
        val conversationResponses: List<ConversationResponse>
)