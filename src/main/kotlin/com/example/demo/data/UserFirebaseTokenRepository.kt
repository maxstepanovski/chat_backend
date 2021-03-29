package com.example.demo.data

import com.example.demo.data.model.UserFirebaseTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserFirebaseTokenRepository: JpaRepository<UserFirebaseTokenEntity, Long> {

    fun findAllByUserId(userId: Long): List<UserFirebaseTokenEntity>
}