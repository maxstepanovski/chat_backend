package com.example.demo.data

import com.example.demo.data.model.UserConversationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserConversationRepository: JpaRepository<UserConversationEntity, Long> {

    fun findAllByUserId(userId: Long): List<UserConversationEntity>
}