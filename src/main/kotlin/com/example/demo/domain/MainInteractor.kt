package com.example.demo.domain

import com.example.demo.controller.model.ConversationResponse
import com.example.demo.data.*
import com.example.demo.data.model.*

class MainInteractor(
        private val conversationRepository: ConversationRepository,
        private val userConversationRepository: UserConversationRepository,
        private val messageRepository: MessageRepository,
        private val conversationMessageRepository: ConversationMessageRepository,
        private val userRepository: UserRepository
) {

    fun getUserConversations(userName: String): List<ConversationResponse> {
        val user = userRepository.findByName(userName)
        val conversationIds = userConversationRepository.findAllByUserId(user.id).map { it.conversationId }
        return conversationRepository.findAllById(conversationIds).map { ConversationResponse(it.id, it.name) }
    }

    fun createNewMessage(principalName: String, userName: String, messageText: String, conversationTitle: String?): Boolean {
        val conversation = conversationRepository.save(ConversationEntity(0, conversationTitle.orEmpty()))
        val user = userRepository.findByName(userName)
        val principal = userRepository.findByName(principalName)

        userConversationRepository.save(UserConversationEntity(0, user.id, conversation.id))
        userConversationRepository.save(UserConversationEntity(0, principal.id, conversation.id))
        val message = messageRepository.save(MessageEntity(0, messageText, System.currentTimeMillis(), principal.id))
        conversationMessageRepository.save(ConversationMessageEntity(0, conversation.id, message.id))

        return true
    }
}