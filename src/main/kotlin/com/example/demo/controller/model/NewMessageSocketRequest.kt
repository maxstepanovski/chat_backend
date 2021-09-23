package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

data class NewMessageSocketRequest(
    @SerializedName(value = "conversation_id")
    val conversationId: Long,

    @SerializedName(value = "message")
    val message: String
) : SocketRequest(SocketRequestType.NEW_MESSAGE.code)