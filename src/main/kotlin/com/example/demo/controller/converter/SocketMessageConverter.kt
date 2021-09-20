package com.example.demo.controller.converter

import com.example.demo.controller.model.BaseSocketRequest
import com.example.demo.controller.model.NewMessageSocketRequest
import com.example.demo.controller.model.SocketRequestType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.springframework.stereotype.Component

@Component
class SocketMessageConverter(private val gson: Gson = GsonBuilder().create()) {

    fun convert(jsonString: String): BaseSocketRequest {
        val json = gson.fromJson(jsonString, JsonObject::class.java)
        if (json == null || json.isJsonNull) {
            throw IllegalArgumentException("неверная структура данных")
        }
        return when (SocketRequestType.fromInt(json.get("request_type").asInt)) {
            SocketRequestType.NEW_MESSAGE -> gson.fromJson(json, NewMessageSocketRequest::class.java)
        }
    }
}