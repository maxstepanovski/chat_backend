package com.example.demo.controller.model

enum class SocketRequestType(val code: Int) {
    NEW_MESSAGE(0);

    companion object {
        fun fromInt(code: Int): SocketRequestType = when (code) {
            0 -> NEW_MESSAGE
            else -> throw IllegalArgumentException("неизвестный тип сообщения")
        }
    }
}