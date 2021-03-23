package com.example.demo.data

import com.example.demo.data.model.UserConversationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserConversationRepository: JpaRepository<UserConversationEntity, Long> {

    @Query(value = "SELECT * FROM public.user_conversation WHERE user_id = ?1", nativeQuery = true)
    fun findAllByUserId(userId: Long): List<UserConversationEntity>
}