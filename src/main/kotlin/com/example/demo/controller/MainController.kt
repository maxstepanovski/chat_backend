package com.example.demo.controller

import com.example.demo.data.MainRepository
import com.example.demo.data.model.ConversationsResponse
import com.example.demo.data.model.IndexResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(private val mainRepository: MainRepository) {

    @GetMapping("/index")
    fun index(): IndexResponse = IndexResponse(
            "Server is working",
            "Use different endpoints to access data"
    )

    @GetMapping("/conversations")
    fun conversations(): ConversationsResponse {
        return ConversationsResponse(
                mainRepository.getUserConversations(SecurityContextHolder.getContext().authentication.principal as String)
        )
    }
}