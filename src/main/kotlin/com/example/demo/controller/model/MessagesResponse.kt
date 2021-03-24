package com.example.demo.controller.model

class MessagesResponse(
        val messages: List<MessageResponse>,
        val page: Int,
        val hasMore: Boolean
)