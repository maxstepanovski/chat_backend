package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

class MessagesResponse(
        @JsonProperty(value = "messages")
        val messages: List<MessageResponse>,

        @JsonProperty(value = "has_more")
        val hasMore: Boolean
)