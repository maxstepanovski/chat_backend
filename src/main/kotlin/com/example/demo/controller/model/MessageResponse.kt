package com.example.demo.controller.model

data class MessageResponse(
        val id: Long,
        val messageText: String,
        val time: Long,
        val userId: Long
)
