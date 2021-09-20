package com.example.demo.controller

import com.example.demo.controller.model.*
import com.example.demo.domain.MainInteractor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(private val mainInteractor: MainInteractor) {

    @GetMapping("/user/exists")
    fun isUserExist(
        @RequestParam(name = "user_name") userName: String
    ): UserExistsResponse = UserExistsResponse(mainInteractor.isUserExists(userName))

    @GetMapping("/user/conversations")
    fun conversations(): ConversationsResponse {
        return ConversationsResponse(
            mainInteractor.getUserConversations(SecurityContextHolder.getContext().authentication.principal as String)
        )
    }

    @GetMapping("/user/messages")
    fun messages(
        @RequestParam(name = "conversation_id") conversationId: Long,
        @RequestParam(name = "page_size") pageSize: Int,
        @RequestParam(name = "last_message_id") lastMessageId: Long
    ): MessagesResponse {
        return mainInteractor.getMessages(
            SecurityContextHolder.getContext().authentication.principal as String,
            conversationId,
            pageSize,
            lastMessageId
        )
    }

    @PostMapping("/user/create_conversation")
    fun createConversation(
        @RequestParam(name = "user_name") userName: String,
        @RequestParam(name = "message") message: String,
        @RequestParam(name = "conversation_title") conversationTitle: String?
    ): NewConversationResponse {
        return NewConversationResponse(
            mainInteractor.createConversation(
                SecurityContextHolder.getContext().authentication.principal as String,
                userName,
                message,
                conversationTitle
            )
        )
    }

    @PostMapping("/user/create_message")
    fun createMessage(
        @RequestParam(name = "message") message: String,
        @RequestParam(name = "conversation_id") conversationId: Long
    ): NewMessageResponse {
        return NewMessageResponse(
            mainInteractor.createMessage(
                SecurityContextHolder.getContext().authentication.principal as String,
                message,
                conversationId
            )
        )
    }

    @PostMapping("/user/firebase_token")
    fun firebaseToken(
        @RequestParam(name = "token") token: String
    ): FirebaseTokenResponse = FirebaseTokenResponse(
        mainInteractor.saveFirebaseToken(SecurityContextHolder.getContext().authentication.principal as String, token)
    )
}