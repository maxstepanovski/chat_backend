package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

data class MessagesSocketResponse(
    @SerializedName(value = "messages")
    val messages: List<MessageSocketResponse>,

    @SerializedName(value = "has_more")
    val hasMore: Boolean
) : SocketResponse(SocketResponseType.MESSAGES.code)