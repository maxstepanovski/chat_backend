package com.example.demo.controller.model

enum class SocketRequestType(val code: Int) {
    NEW_MESSAGE(0), MESSAGES(1), UNKNOWN(666);

    companion object {
        fun fromInt(code: Int): SocketRequestType = when (code) {
            0 -> NEW_MESSAGE
            1 -> MESSAGES
            else -> UNKNOWN
        }
    }
}