package com.example.demo.data

import com.example.demo.data.model.ConversationMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationMessageRepository: JpaRepository<ConversationMessageEntity, Long>