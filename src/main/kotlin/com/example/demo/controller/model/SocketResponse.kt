package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

open class SocketResponse(
    @SerializedName(value = "response_type")
    val responseType: Int
)