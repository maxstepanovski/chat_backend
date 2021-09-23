package com.example.demo.controller.model

enum class SocketResponseType(val code: Int) {
    MESSAGE(0), MESSAGES(1), INFO(2), UNKNOWN(666)
}