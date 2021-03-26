package com.example.demo.data

import com.example.demo.data.model.ConversationMessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ConversationMessageRepository : JpaRepository<ConversationMessageEntity, Long> {

    fun findAllByConversationId(conversationId: Long, pageable: Pageable): Page<ConversationMessageEntity>


    @Query(
            "SELECT EXISTS(SELECT * FROM public.conversation_message WHERE conversation_id = ?1 AND message_id < ?2)",
            nativeQuery = true
    )
    fun existsWithIdLessThan(conversationId: Long, messageId: Long): Boolean

    @Query(
            "SELECT * FROM public.conversation_message WHERE conversation_id = ?1 ORDER BY message_id DESC LIMIT ?2",
            nativeQuery = true
    )
    fun findAllByConversationId(conversationId: Long, pageSize: Int): List<ConversationMessageEntity>

    @Query(
            "SELECT * FROM public.conversation_message WHERE conversation_id = ?1 AND message_id < ?2 ORDER BY message_id DESC LIMIT ?3",
            nativeQuery = true
    )
    fun findAllByConversationId(conversationId: Long, lastMessageId: Long, pageSize: Int): List<ConversationMessageEntity>
}