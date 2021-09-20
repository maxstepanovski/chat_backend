package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

open class BaseSocketRequest(
    @JsonProperty(value = "request_type")
    val requestType: SocketRequestType
)