package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageResponse(
        @JsonProperty(value = "id")
        val id: Long,

        @JsonProperty(value = "message_text")
        val messageText: String,

        @JsonProperty(value = "time")
        val time: Long,

        @JsonProperty(value = "sender_name")
        val senderName: String,

        @JsonProperty(value = "is_principal")
        val isPrincipal: Boolean
)
