package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationResponse(
        @JsonProperty(value = "is_successful")
        val isSuccessful: Boolean
)