package com.example.demo.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class IndexResponse(
        @JsonProperty(value = "title")
        val title: String,

        @JsonProperty(value = "description")
        val description: String
)