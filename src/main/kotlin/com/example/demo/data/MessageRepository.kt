package com.example.demo.data

import com.example.demo.data.model.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<MessageEntity, Long>