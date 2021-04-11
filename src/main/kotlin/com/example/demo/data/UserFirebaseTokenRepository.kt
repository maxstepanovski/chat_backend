package com.example.demo.data

import com.example.demo.data.model.UserFirebaseTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserFirebaseTokenRepository: JpaRepository<UserFirebaseTokenEntity, Long> {

    @Query("DELETE FROM public.user_firebase_token WHERE user_id = ?1", nativeQuery = true)
    fun deleteAllByUserId(userId: Long)

    fun findAllByUserId(userId: Long): List<UserFirebaseTokenEntity>
}