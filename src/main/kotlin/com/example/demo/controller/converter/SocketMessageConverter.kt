package com.example.demo.controller.converter

import com.example.demo.controller.model.*
import com.example.demo.data.model.MessageEntity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage

@Component
class SocketMessageConverter(private val gson: Gson = GsonBuilder().create()) {

    fun convert(jsonString: String): SocketRequest {
        val json = gson.fromJson(jsonString, JsonObject::class.java)
        if (json == null || json.isJsonNull) {
            UnknownSocketRequest()
        }
        return when (SocketRequestType.fromInt(json.get("request_type").asInt)) {
            SocketRequestType.NEW_MESSAGE -> gson.fromJson(json, NewMessageSocketRequest::class.java)
            else -> UnknownSocketRequest()
        }
    }

    fun convert(sender: String, isPrincipal: Boolean, message: MessageEntity): TextMessage {
        val messageResponse = MessageSocketResponse(
            id = message.id,
            messageText = message.text,
            time = message.time,
            senderName = sender,
            isPrincipal = isPrincipal
        )
        return TextMessage(gson.toJson(messageResponse))
    }

    fun convert(infoMessage: InfoSocketResponse): TextMessage {
        return TextMessage(gson.toJson(infoMessage))
    }
}