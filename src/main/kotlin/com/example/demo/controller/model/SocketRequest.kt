package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

open class SocketRequest(
    @SerializedName(value = "request_type")
    val requestType: SocketRequestType
)