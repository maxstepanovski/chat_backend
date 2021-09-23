package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

data class MessagesSocketRequest(
    @SerializedName(value = "conversation_id")
    val conversationId: Long,

    @SerializedName(value = "page_size")
    val pageSize: Int,

    @SerializedName(value = "last_message_id")
    val lastMessageId: Long
) : SocketRequest(SocketRequestType.MESSAGES.code)