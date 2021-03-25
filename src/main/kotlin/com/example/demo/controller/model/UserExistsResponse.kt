package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserExistsResponse(
        @JsonProperty(value = "is_exists")
        val isExists: Boolean
)