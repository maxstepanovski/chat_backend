package com.example.demo.controller.model

import com.google.gson.annotations.SerializedName

class InfoSocketResponse(
    @SerializedName(value = "info_message")
    val infoMessage: String
) : SocketResponse(SocketResponseType.INFO.code)