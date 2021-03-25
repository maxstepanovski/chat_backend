package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
        @JsonProperty(value = "id")
        val id: Long,

        @JsonProperty(value = "name")
        val name: String
)