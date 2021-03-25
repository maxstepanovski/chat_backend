package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageResponse(
        @JsonProperty(value = "id")
        val id: Long,

        @JsonProperty(value = "message_text")
        val messageText: String,

        @JsonProperty(value = "time")
        val time: Long,

        @JsonProperty(value = "user_id")
        val userId: Long
)
