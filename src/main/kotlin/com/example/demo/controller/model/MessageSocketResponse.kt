package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

class MessageSocketResponse(
    @SerializedName(value = "id")
    val id: Long,

    @SerializedName(value = "message_text")
    val messageText: String,

    @SerializedName(value = "time")
    val time: Long,

    @SerializedName(value = "sender_name")
    val senderName: String,

    @SerializedName(value = "is_principal")
    val isPrincipal: Boolean
) : SocketResponse(SocketResponseType.MESSAGE.code)