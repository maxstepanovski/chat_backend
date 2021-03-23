package com.example.demo.controller

import com.example.demo.domain.MainInteractor
import com.example.demo.controller.model.ConversationsResponse
import com.example.demo.controller.model.IndexResponse
import com.example.demo.controller.model.NewMessageResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(private val mainInteractor: MainInteractor) {

    @GetMapping("/index")
    fun index(): IndexResponse = IndexResponse("App is running", "use api endpoints")

    @GetMapping("/conversations")
    fun conversations(): ConversationsResponse {
        return ConversationsResponse(
                mainInteractor.getUserConversations(SecurityContextHolder.getContext().authentication.principal as String)
        )
    }

    @PostMapping("/send_new_message")
    fun sendNewMessage(
            @RequestParam(name = "user_name") userName: String,
            @RequestParam(name = "message") message: String,
            @RequestParam(name = "conversation_title") conversationTitle: String?
    ): NewMessageResponse {
        val principalName = SecurityContextHolder.getContext().authentication.principal as String
        return NewMessageResponse(
                mainInteractor.createNewMessage(principalName, userName, message, conversationTitle)
        )
    }
}