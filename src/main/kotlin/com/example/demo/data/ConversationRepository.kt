package com.example.demo.data

import com.example.demo.data.model.ConversationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationRepository: JpaRepository<ConversationEntity, Long> {

}