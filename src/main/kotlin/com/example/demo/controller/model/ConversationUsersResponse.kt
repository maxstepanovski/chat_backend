package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ConversationUsersResponse(
        @JsonProperty(value = "conversation_users")
        val conversationUsers: List<UserResponse>
)
