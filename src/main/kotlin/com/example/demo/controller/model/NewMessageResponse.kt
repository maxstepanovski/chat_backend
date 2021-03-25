package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NewMessageResponse(
        @JsonProperty(value = "is_successful")
        val isSuccessful: Boolean
)