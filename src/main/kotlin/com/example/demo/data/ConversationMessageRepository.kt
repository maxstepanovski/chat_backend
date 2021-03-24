package com.example.demo.data

import com.example.demo.data.model.ConversationMessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationMessageRepository: JpaRepository<ConversationMessageEntity, Long> {

    fun findAllByConversationId(conversationId: Long, pageable: Pageable): Page<ConversationMessageEntity>
}