package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FirebaseTokenResponse(
        @JsonProperty(value = "is_successful")
        val isSuccessful: Boolean
)